package ru.yandex.practicum.filmorate.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.yandex.practicum.filmorate.adapter.LocalDateAdapter;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserService {
    private static final Map<Long, User> users = new HashMap<>();
    private static Long lastId = 0L;
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();

    public static void saveUser(User user) {
        if (user.getName() == null || user.getName().equals("")) {
            user.setName(user.getLogin());
        }
        user.setId(++lastId);
        users.put(user.getId(), user);
    }

    public static String userToJson(User user) {
        return gson.toJson(user);
    }

    public static void updateUser(User user) {
        if (!users.containsKey(user.getId())) {
            throw new NotFoundException(String.format("User with id=%s not found", user.getId()));
        }
        users.put(user.getId(), user);
    }

    public static String getUsers() {
        return gson.toJson(new ArrayList<>(users.values()));
    }
}
