package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.FriendStorage;
import ru.yandex.practicum.filmorate.storage.dao.UserStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final FriendStorage friendStorage;

    public void addFriendById(Integer id, Integer friendId) {
        friendStorage.addFriend(id, friendId);
    }

    public List<User> getFriendsCommon(Integer id, Integer otherId) {
        return friendStorage.getCommonFriends(id, otherId);
    }

    public List<User> getFriends(Integer id) {
        return friendStorage.getFriends(id);
    }

    public void removeFriendById(Integer id, Integer friendId) {
        friendStorage.removeFriend(id, friendId);
    }
}
