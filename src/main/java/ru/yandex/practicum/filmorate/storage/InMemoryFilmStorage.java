package ru.yandex.practicum.filmorate.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.adapter.DurationAdapter;
import ru.yandex.practicum.filmorate.adapter.LocalDateAdapter;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage{
    private final Map<Long, Film> films = new HashMap<>();
    private Long lastId = 0L;
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(Duration.class, new DurationAdapter())
            .create();

    @Override
    public void saveFilm(Film film) {
        if (film.getDuration().isNegative()) {
            throw new ValidationException("Duration can't be zero or negative");
        }
        if (film.getReleaseDate().isBefore(LocalDate.parse("1895-12-28"))) {
            throw new ValidationException("Release date can't be more than 1895-12-28");
        }
        film.setId(++lastId);
        films.put(film.getId(), film);
    }

    @Override
    public String filmToJson(Film film) {
        return gson.toJson(film);
    }

    @Override
    public void updateFilm(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new NotFoundException(String.format("Film with id=%s not found", film.getId()));
        }
        films.put(film.getId(), film);
    }

    @Override
    public String getFilms() {
        return gson.toJson(new ArrayList<>(films.values()));
    }
}
