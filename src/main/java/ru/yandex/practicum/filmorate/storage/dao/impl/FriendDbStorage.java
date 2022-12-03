package ru.yandex.practicum.filmorate.storage.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.FriendStorage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class FriendDbStorage implements FriendStorage {
    private final JdbcTemplate jdbcTemplate;
    private final UserDbStorage userDbStorage;

    private final Logger logger = LoggerFactory.getLogger(FriendDbStorage.class);

    @Autowired
    public FriendDbStorage(JdbcTemplate jdbcTemplate, UserDbStorage userDbStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDbStorage = userDbStorage;
    }

    @Override
    public void addFriend(Integer userId, Integer friendId) {
        if (!userDbStorage.isUserExist(userId)) {
            throw new NotFoundException(String.format("User with id=%s not found", userId));
        }
        if (!userDbStorage.isUserExist(friendId)) {
            throw new NotFoundException(String.format("Other user with id=%s not found", friendId));
        }
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(
                "select * from FRIENDS where USER_ID = ? and FRIEND_ID = ?", friendId, userId);
        if (sqlRowSet.next()) {
            jdbcTemplate.update("update FRIENDS set IS_APPROVED = true where USER_ID = ? and FRIEND_ID = ?",
                    friendId, userId);
            logger.info("Friendship approved");
        } else {
            jdbcTemplate.update("insert into FRIENDS(user_id, friend_id) VALUES (?, ?)",
                    userId, friendId);
            logger.info("The request for confirmation of friendship has been sent");
        }
    }

    @Override
    public void removeFriend(Integer userId, Integer friendId) {
        if (!userDbStorage.isUserExist(userId)) {
            throw new NotFoundException(String.format("User with id=%s not found", userId));
        }
        if (!userDbStorage.isUserExist(friendId)) {
            throw new NotFoundException(String.format("Other user with id=%s not found", friendId));
        }
        jdbcTemplate.update("delete from FRIENDS where USER_ID = ? and FRIEND_ID = ?", userId, friendId);
        jdbcTemplate.update("delete from FRIENDS where USER_ID = ? and FRIEND_ID = ?", friendId, userId);
        logger.info("Friendship is broken, friendship requests are canceled");
    }

    @Override
    public List<User> getFriends(Integer id) {
        if (!userDbStorage.isUserExist(id)) {
            throw new NotFoundException(String.format("User with id=%s not found", id));
        }
        List<User> friends = new ArrayList<>();
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(
                "select * from FRIENDS " +
                        "where USER_ID = ? or (FRIEND_ID = ? and IS_APPROVED = true) " +
                        "order by FRIEND_ID", id, id);
        while (sqlRowSet.next()) {
            friends.add(userDbStorage.getUserById(sqlRowSet.getInt("friend_id")));
        }
        return friends;
    }

    @Override
    public List<User> getCommonFriends(Integer userId, Integer otherId) {
        if (!userDbStorage.isUserExist(userId)) {
            throw new NotFoundException(String.format("User with id=%s not found", userId));
        }
        if (!userDbStorage.isUserExist(otherId)) {
            throw new NotFoundException(String.format("Other user with id=%s not found", otherId));
        }
        Set<User> userFriends = new HashSet<>();
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(
                "select * from FRIENDS " +
                        "where USER_ID = ? or (FRIEND_ID = ? and IS_APPROVED = true) " +
                        "order by FRIEND_ID", userId, userId);
        while (sqlRowSet.next()) {
            userFriends.add(userDbStorage.getUserById(sqlRowSet.getInt("friend_id")));
        }
        Set<User> otherFriends = new HashSet<>();
        sqlRowSet = jdbcTemplate.queryForRowSet(
                "select * from FRIENDS " +
                        "where USER_ID = ? or (FRIEND_ID = ? and IS_APPROVED = true) " +
                        "order by FRIEND_ID", otherId, otherId);
        while (sqlRowSet.next()) {
            otherFriends.add(userDbStorage.getUserById(sqlRowSet.getInt("friend_id")));
        }
        Set<User> intersection = new HashSet<>(userFriends);
        intersection.retainAll(otherFriends);
        return new ArrayList<>(intersection);
    }
}
