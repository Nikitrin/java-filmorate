package ru.yandex.practicum.filmorate.adapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Service
public class GsonFilmSerialize {
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(Duration.class, new DurationAdapter())
            .create();

    public static String toJson(Film film) {
        return gson.toJson(film);
    }

    public static String toJson(List<Film> films) {
        return gson.toJson(films);
    }
}
