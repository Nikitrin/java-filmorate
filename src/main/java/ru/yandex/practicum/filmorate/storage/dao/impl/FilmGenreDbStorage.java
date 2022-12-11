package ru.yandex.practicum.filmorate.storage.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.dao.FilmGenresStorage;
import ru.yandex.practicum.filmorate.storage.dao.GenreStorage;

import java.util.ArrayList;
import java.util.List;

@Component
public class FilmGenreDbStorage implements FilmGenresStorage {
    private final GenreStorage genreStorage;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmGenreDbStorage(GenreStorage genreStorage, JdbcTemplate jdbcTemplate) {
        this.genreStorage = genreStorage;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addGenreToFilm(Integer filmId, Integer genreId) {
        jdbcTemplate.update("insert into FILM_GENRES(film, genre) VALUES (?, ?)", filmId, genreId);
    }

    @Override
    public void removeGenreFromFilm(Integer filmId, Integer genreId) {
        jdbcTemplate.update("delete from FILM_GENRES where GENRE = ? and FILM = ?", genreId, filmId);
    }

    @Override
    public void removeAllGenresFromFilm(Integer filmId) {
        jdbcTemplate.update("delete from FILM_GENRES where FILM = ?", filmId);
    }

    @Override
    public List<Genre> getFilmGenres(Integer filmId) {
        List<Genre> genres = new ArrayList<>();
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("" +
                "select GENRES.ID, GENRES.GENRE from FILM_GENRES " +
                "JOIN GENRES on GENRES.ID = FILM_GENRES.GENRE " +
                "WHERE FILM = ?", filmId);
        while (sqlRowSet.next()) {
            genres.add(genreStorage.makeGenre(sqlRowSet));
        }
        return genres;
    }
}
