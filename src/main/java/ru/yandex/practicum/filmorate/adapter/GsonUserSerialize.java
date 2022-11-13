package ru.yandex.practicum.filmorate.adapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import java.time.LocalDate;
import java.util.List;

@Service
public class GsonUserSerialize {
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .excludeFieldsWithoutExposeAnnotation()
            .create();

    public static String toJson(User user) {
        return gson.toJson(user);
    }

    public static String toJson(List<User> users) {
        return gson.toJson(users);
    }
}
