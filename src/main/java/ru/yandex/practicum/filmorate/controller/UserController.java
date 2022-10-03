package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import javax.validation.Valid;

@RestController
@RequestMapping(
        value = "/users",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public String saveUserJson(@Valid @RequestBody User user) {
        logger.info("Save user from json: {}", user);
        UserService.saveUser(user);
        return UserService.userToJson(user);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public String updateUser(@Valid @RequestBody User user) {
        logger.info("Update user from json: {}", user);
        UserService.updateUser(user);
        return UserService.userToJson(user);
    }

    @GetMapping()
    public String getUsers() {
        logger.info("Get all users");
        return UserService.getUsers();
    }
}
