package ru.yandex.practicum.filmorate.storage.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.dao.FilmLikesStorage;
import ru.yandex.practicum.filmorate.storage.dao.FilmStorage;
import ru.yandex.practicum.filmorate.storage.dao.UserStorage;
import java.util.ArrayList;
import java.util.List;

@Component
public class FilmLikesDbStorage implements FilmLikesStorage {
    private final JdbcTemplate jdbcTemplate;
    private final UserStorage userStorage;
    private final FilmStorage filmStorage;

    @Autowired
    public FilmLikesDbStorage(JdbcTemplate jdbcTemplate, UserStorage userStorage, FilmStorage filmStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.userStorage = userStorage;
        this.filmStorage = filmStorage;
    }

    @Override
    public List<Film> getFilmPopular(Integer count) {
        List<Film> films = new ArrayList<>();
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("" +
                "select FILMS.ID from FILMS " +
                "left join FILM_LIKES on FILM_LIKES.FILM = FILMS.ID " +
                "group by FILMS.ID " +
                "order by count(FILM_LIKES.USER_LIKE) desc " +
                "limit ?", count);
        while (sqlRowSet.next()) {
            films.add(filmStorage.getFilmById(sqlRowSet.getInt("id")));
        }
        return films;
    }

    @Override
    public void addLikeToFilm(Integer id, Integer userId) {
        if (!filmStorage.isFilmExist(id)) {
            throw new NotFoundException("Film not found");
        }
        if (!userStorage.isUserExist(userId)) {
            throw new NotFoundException("User not found");
        }
        if (jdbcTemplate.queryForRowSet(
                "select * from FILM_LIKES where FILM = ? and USER_LIKE = ?", id, userId).next()) {
            throw new ValidationException("User is already likes film");
        }
        jdbcTemplate.update("insert into FILM_LIKES(film, USER_LIKE) VALUES (?, ?)", id, userId);
    }

    @Override
    public void removeLikeFromFilm(Integer id, Integer userId) {
        if (!filmStorage.isFilmExist(id)) {
            throw new NotFoundException("Film not found");
        }
        if (!userStorage.isUserExist(userId)) {
            throw new NotFoundException("User not found");
        }
        if (!jdbcTemplate.queryForRowSet(
                "select * from FILM_LIKES where FILM = ? and USER_LIKE = ?", id, userId).next()) {
            throw new ValidationException("User is not likes film");
        }
        jdbcTemplate.update("delete from FILM_LIKES where FILM = ? and USER_LIKE = ?", id, userId);
    }
}
