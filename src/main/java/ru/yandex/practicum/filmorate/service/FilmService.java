package ru.yandex.practicum.filmorate.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.yandex.practicum.filmorate.adapter.DurationAdapter;
import ru.yandex.practicum.filmorate.adapter.LocalDateAdapter;
import ru.yandex.practicum.filmorate.exception.InvalidInput;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FilmService {
    private static final Map<Long, Film> films = new HashMap<>();
    private static Long lastId = 0L;
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(Duration.class, new DurationAdapter())
            .create();

    public static void saveFilm(Film film) {
        if (film.getDuration().isNegative()) {
            throw new InvalidInput("Duration can't be zero or negative");
        }
        if (film.getReleaseDate().isBefore(LocalDate.parse("1895-12-28"))) {
            throw new InvalidInput("Release date can't be more than 1895-12-28");
        }
        film.setId(++lastId);
        films.put(film.getId(), film);
    }

    public static String filmToJson(Film film) {
        return gson.toJson(film);
    }

    public static void updateFilm(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new NotFoundException(String.format("Film with id=%s not found", film.getId()));
        }
        films.put(film.getId(), film);
    }

    public static String getFilms() {
        return gson.toJson(new ArrayList<>(films.values()));
    }
}
