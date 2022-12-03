package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.model.Film;
import java.util.List;

public interface FilmLikesStorage {

    List<Film> getFilmPopular(Integer count);

    void addLikeToFilm(Integer id, Integer userId);

    void removeLikeFromFilm(Integer id, Integer userId);
}
