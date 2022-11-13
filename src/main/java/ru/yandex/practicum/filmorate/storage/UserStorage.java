package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;
import java.util.List;

public interface UserStorage {
    User saveUser(User user);

    User updateUser(User user);

    List<User> getUsers();

    Boolean isUserExist(Long id);

    User getUserById(Long id);
}
