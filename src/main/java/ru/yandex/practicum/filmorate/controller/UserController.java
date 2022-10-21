package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import javax.validation.Valid;

@RestController
@RequestMapping(
        value = "/users",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class UserController {
    private final UserStorage userStorage;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public String saveUserJson(@Valid @RequestBody User user) {
        logger.info("Save user from json: {}", user);
        userStorage.saveUser(user);
        return userStorage.userToJson(user);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public String updateUser(@Valid @RequestBody User user) {
        logger.info("Update user from json: {}", user);
        userStorage.updateUser(user);
        return userStorage.userToJson(user);
    }

    @GetMapping()
    public String getUsers() {
        logger.info("Get all users");
        return userStorage.getUsers();
    }
}
