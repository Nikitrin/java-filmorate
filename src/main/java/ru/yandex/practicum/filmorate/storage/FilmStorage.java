package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import java.util.List;

public interface FilmStorage {
    Film saveFilm(Film film);

    Film updateFilm(Film film);

    List<Film> getFilms();

    Film getFilmsById(Long id);

    Boolean isFilmExist(Long id);
}