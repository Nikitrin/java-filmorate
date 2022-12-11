package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.adapter.GsonUserSerialize;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.dao.UserStorage;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(
        value = "/users",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class UserController {
    private final UserStorage userStorage;
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public String saveUserJson(@Valid @RequestBody User user) {
        logger.info("Save user from json: {}", user);
        return GsonUserSerialize.toJson(userStorage.saveUser(user));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public String updateUser(@Valid @RequestBody User user) {
        logger.info("Update user from json: {}", user);
        return GsonUserSerialize.toJson(userStorage.updateUser(user));
    }

    @GetMapping()
    public String getUsers() {
        logger.info("Get all users");
        return GsonUserSerialize.toJson(userStorage.getUsers());
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable Integer id) {
        logger.info("Get user by id=" + id);
        return GsonUserSerialize.toJson(userStorage.getUserById(id));
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriendById(@PathVariable Integer id, @PathVariable Integer friendId) {
        logger.info(String.format("User id=%s add friend id=%s", id, friendId));
        userService.addFriendById(id, friendId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public String getFriendsCommon(@PathVariable Integer id, @PathVariable Integer otherId) {
        logger.info(String.format("Common friends for users id=%s add id=%s", id, otherId));
        return GsonUserSerialize.toJson(userService.getFriendsCommon(id, otherId));
    }

    @GetMapping("/{id}/friends")
    public String getFriends(@PathVariable Integer id) {
        logger.info("Get user's friends, id=" + id);
        return GsonUserSerialize.toJson(userService.getFriends(id));
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        logger.info(String.format("User id=%s remove friend id=%s", id, friendId));
        userService.removeFriendById(id, friendId);
    }
}