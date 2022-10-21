package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

public interface FilmStorage {
    void saveFilm(Film film);

    String filmToJson(Film film);

    void updateFilm(Film film);

    String getFilms();
}
