package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public List<User> addFriendById(Long id, Long friendId) {
        if (!userStorage.isUserExist(id)) {
            throw new NotFoundException(String.format("User with id=%s not found", id));
        }
        if (!userStorage.isUserExist(friendId)) {
            throw new NotFoundException(String.format("Friend with id=%s not found", friendId));
        }
        if (id.equals(friendId)) {
            throw new ValidationException("You can't add yourself as a friend");
        }
        if (userStorage.getUserById(id).getFriends().contains(friendId)) {
            throw new ValidationException(
                    String.format("User with an id=%s has already been added to friends", friendId));
        }
        userStorage.getUserById(id).getFriends().add(friendId);
        userStorage.getUserById(friendId).getFriends().add(id);
        return Arrays.asList(userStorage.getUserById(friendId), userStorage.getUserById(id));
    }

    public List<User> getFriendsCommon(Long id, Long otherId) {
        if (!userStorage.isUserExist(id)) {
            throw new NotFoundException(String.format("User with id=%s not found", id));
        }
        if (!userStorage.isUserExist(otherId)) {
            throw new NotFoundException(String.format("Other user with id=%s not found", otherId));
        }
        Set<Long> intersection = new HashSet<>(userStorage.getUserById(id).getFriends());
        intersection.retainAll(userStorage.getUserById(otherId).getFriends());
        return intersection.stream()
                .map(userStorage::getUserById)
                .collect(Collectors.toList());
    }

    public List<User> getFriends(Long id) {
        if (!userStorage.isUserExist(id)) {
            throw new NotFoundException(String.format("User with id=%s not found", id));
        }
        return userStorage.getUserById(id).getFriends().stream()
                .map(userStorage::getUserById)
                .collect(Collectors.toList());
    }

    public List<User> removeFriendById(Long id, Long friendId) {
        if (!userStorage.isUserExist(id)) {
            throw new NotFoundException(String.format("User with id=%s not found", id));
        }
        if (!userStorage.isUserExist(friendId)) {
            throw new NotFoundException(String.format("Friend with id=%s not found", friendId));
        }
        if (!userStorage.getUserById(id).getFriends().contains(friendId)) {
            throw new ValidationException(
                    String.format("User with an id=%s does not have friend with id=%s", id, friendId));
        }
        userStorage.getUserById(id).getFriends().remove(friendId);
        userStorage.getUserById(friendId).getFriends().remove(id);
        return Arrays.asList(userStorage.getUserById(id), userStorage.getUserById(friendId));
    }
}
