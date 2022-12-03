package ru.yandex.practicum.filmorate.storage.inmemory;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.UserStorage;

import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private Integer lastId = 0;

    @Override
    public User saveUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(++lastId);
      //  user.setFriends(new HashSet<>());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (!users.containsKey(user.getId())) {
            throw new NotFoundException(String.format("User with id=%s not found", user.getId()));
        }
        User updateUser = users.get(user.getId());
        updateUser.setName(user.getName());
        updateUser.setLogin(user.getLogin());
        updateUser.setEmail(user.getEmail());
        updateUser.setBirthday(user.getBirthday());
        return updateUser;
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public Boolean isUserExist(Integer id) {
        return users.containsKey(id);
    }

    @Override
    public User getUserById(Integer id) {
        if (!users.containsKey(id)) {
            throw new NotFoundException(String.format("User with id=%s not found", id));
        }
        return users.get(id);
    }
}
