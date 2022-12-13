package ru.yandex.practicum.filmorate.storage.dao.impl;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.UserStorage;

import java.util.ArrayList;
import java.util.List;

@Component
@Primary
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User saveUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (jdbcTemplate.queryForRowSet("select * from USERS where EMAIL like ?", user.getEmail()).next()) {
            throw new ValidationException("Email already exists");
        }
        if (jdbcTemplate.queryForRowSet("select * from USERS where LOGIN like ?", user.getLogin()).next()) {
            throw new ValidationException("Login already exists");
        }

        jdbcTemplate.update("insert into USERS(email, login, name, birthday) values (?, ?, ?, ?)",
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday());

        return makeUser(jdbcTemplate.queryForRowSet("select * from USERS where EMAIL like ?", user.getEmail()));
    }

    @Override
    public User updateUser(User user) {
        if (!jdbcTemplate.queryForRowSet("select * from USERS where id = ?", user.getId()).next()) {
            throw new NotFoundException(String.format("User with id=%s not found", user.getId()));
        }

        jdbcTemplate.update("update USERS set email = ?, login = ?, name= ?, birthday= ? where ID = ?",
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId());

        return makeUser(jdbcTemplate.queryForRowSet("select * from USERS where id = ?", user.getId()));
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select * from USERS order by ID");
        while (userRows.next()) {
            users.add(new User(
                    userRows.getInt("id"),
                    userRows.getString("email"),
                    userRows.getString("login"),
                    userRows.getString("name"),
                    userRows.getDate("birthday").toLocalDate()));
        }
        return users;
    }

    @Override
    public Boolean isUserExist(Integer id) {
        return jdbcTemplate.queryForRowSet("select * from USERS where id = ?", id).next();
    }

    @Override
    public User getUserById(Integer id) {
        if (!jdbcTemplate.queryForRowSet("select * from USERS where id = ?", id).next()) {
            throw new NotFoundException(String.format("User with id=%s not found", id));
        }
        return makeUser(jdbcTemplate.queryForRowSet("select * from USERS where id = ?", id));
    }

    private User makeUser(SqlRowSet sqlRowSet) {
        sqlRowSet.next();
        return new User(
                sqlRowSet.getInt("id"),
                sqlRowSet.getString("email"),
                sqlRowSet.getString("login"),
                sqlRowSet.getString("name"),
                sqlRowSet.getDate("birthday").toLocalDate());
    }
}
