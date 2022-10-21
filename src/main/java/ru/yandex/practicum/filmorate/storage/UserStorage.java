package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

public interface UserStorage {
    void saveUser(User user);

    String userToJson(User user);

    void updateUser(User user);

    String getUsers();
}
