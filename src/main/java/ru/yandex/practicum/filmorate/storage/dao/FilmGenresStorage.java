package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface FilmGenresStorage {

    void addGenreToFilm(Integer filmId, Integer genreId);

    void removeGenreFromFilm(Integer filmId, Integer genreId);

    void removeAllGenresFromFilm(Integer filmId);

    List<Genre> getFilmGenres(Integer filmId);
}
