package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;
import java.util.*;

@Component
public class InMemoryFilmStorage implements FilmStorage{
    private final Map<Long, Film> films = new HashMap<>();
    private Long lastId = 0L;

    @Override
    public Film saveFilm(Film film) {
        if (film.getDuration().isNegative()) {
            throw new ValidationException("Duration can't be zero or negative");
        }
        if (film.getReleaseDate().isBefore(LocalDate.parse("1895-12-28"))) {
            throw new ValidationException("Release date can't be more than 1895-12-28");
        }
        film.setId(++lastId);
        film.setLikes(new HashSet<>());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new NotFoundException(String.format("Film with id=%s not found", film.getId()));
        }
        Film updateFilm = films.get(film.getId());
        updateFilm.setName(film.getName());
        updateFilm.setDescription(film.getDescription());
        updateFilm.setDuration(film.getDuration());
        updateFilm.setReleaseDate(film.getReleaseDate());
        return updateFilm;
    }

    @Override
    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film getFilmById(Long id) {
        if (!films.containsKey(id)) {
            throw new NotFoundException(String.format("Film with id=%s not found", id));
        }
        return films.get(id);
    }

    @Override
    public Boolean isFilmExist(Long id) {
        return films.containsKey(id);
    }
}
