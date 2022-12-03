package ru.yandex.practicum.filmorate.storage.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.dao.FilmGenresStorage;
import ru.yandex.practicum.filmorate.storage.dao.FilmStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Primary
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final FilmGenresStorage filmGenresStorage;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate, FilmGenresStorage filmGenresStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.filmGenresStorage = filmGenresStorage;
    }

    @Override
    public Film saveFilm(Film film) {
        if (film.getDuration() <= 0) {
            throw new ValidationException("Duration can't be zero or negative");
        }
        if (film.getReleaseDate().isBefore(LocalDate.parse("1895-12-28"))) {
            throw new ValidationException("Release date can't be more than 1895-12-28");
        }
        if (jdbcTemplate.queryForRowSet("select * from FILMS where TITLE like ?", film.getName()).next()) {
            throw new ValidationException("Film already exists");
        }

        jdbcTemplate.update("insert into FILMS(TITLE, DESCRIPTION, REALISE_DATE, DURATION, MPA_RATING) " +
                        "values (?, ?, ?, ?, ?)",
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId());

        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(
                "select ID from FILMS where TITLE like ?", film.getName());
        sqlRowSet.next();
        Integer filmId = sqlRowSet.getInt("id");
        if (film.getGenres() != null) {
            for (Genre genre : film.getGenres()) {
                filmGenresStorage.addGenreToFilm(filmId, genre.getId());
            }
        }
        return makeFilm(jdbcTemplate.queryForRowSet("select * from FILMS where TITLE like ?", film.getName()));
    }

    @Override
    public Film updateFilm(Film film) {
        if (film.getDuration() <= 0) {
            throw new ValidationException("Duration can't be zero or negative");
        }
        if (film.getReleaseDate().isBefore(LocalDate.parse("1895-12-28"))) {
            throw new ValidationException("Release date can't be more than 1895-12-28");
        }
        if (!jdbcTemplate.queryForRowSet("select * from FILMS where ID = ?", film.getId()).next()) {
            throw new NotFoundException("Film not found");
        }

        jdbcTemplate.update("update FILMS set TITLE = ?, DESCRIPTION = ?, REALISE_DATE = ?, " +
                        "DURATION = ?, MPA_RATING = ? where ID = ?",
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());

        filmGenresStorage.removeAllGenresFromFilm(film.getId());
        if (film.getGenres() != null) {
            for (Genre genre : film.getGenres()) {
                filmGenresStorage.addGenreToFilm(film.getId(), genre.getId());
            }
        }
        return makeFilm(jdbcTemplate.queryForRowSet("select * from FILMS where ID = ?", film.getId()));
    }


    @Override
    public List<Film> getFilms() {
        List<Film> films = new ArrayList<>();
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("select * from FILMS order by ID");
        while (sqlRowSet.next()) {
            films.add(new Film(
                    sqlRowSet.getInt("id"),
                    sqlRowSet.getString("title"),
                    sqlRowSet.getString("description"),
                    sqlRowSet.getDate("realise_date").toLocalDate(),
                    sqlRowSet.getInt("duration"),
                    getMpaRating(sqlRowSet.getInt("mpa_rating")),
                    new HashSet<>(filmGenresStorage.getFilmGenres(sqlRowSet.getInt("id")))));
        }
        return films;
    }

    @Override
    public Film getFilmById(Integer id) {
        if (!jdbcTemplate.queryForRowSet("select * from FILMS where ID = ?", id).next()) {
            throw new NotFoundException("Film not found");
        }
        return makeFilm(jdbcTemplate.queryForRowSet("select * from FILMS where ID = ?", id));
    }

    @Override
    public Boolean isFilmExist(Integer id) {
        return jdbcTemplate.queryForRowSet("select * from FILMS where ID = ?", id).next();
    }

    private Film makeFilm(SqlRowSet sqlRowSet) {
        sqlRowSet.next();
        return new Film(
                sqlRowSet.getInt("id"),
                sqlRowSet.getString("title"),
                sqlRowSet.getString("description"),
                sqlRowSet.getDate("realise_date").toLocalDate(),
                sqlRowSet.getInt("duration"),
                getMpaRating(sqlRowSet.getInt("mpa_rating")),
                new HashSet<>(filmGenresStorage.getFilmGenres(sqlRowSet.getInt("id"))));
    }

    private MpaRating getMpaRating(Integer id) {
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("select RATING from MPA_RATINGS where ID = ?", id);
        sqlRowSet.next();
        return new MpaRating(id, sqlRowSet.getString("rating"));
    }
}
