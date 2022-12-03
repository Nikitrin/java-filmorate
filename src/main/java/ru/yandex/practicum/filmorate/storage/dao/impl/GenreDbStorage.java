package ru.yandex.practicum.filmorate.storage.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.dao.GenreStorage;
import java.util.ArrayList;
import java.util.List;

@Component
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Genre getGenreById(Integer id) {
        SqlRowSet genderRows = jdbcTemplate.queryForRowSet("select * from GENRES where ID = ?", id);
        if (genderRows.next()) {
            return makeGenre(genderRows);
        } else {
            throw new NotFoundException(String.format("Genre with id=%s not found", id));
        }
    }

    @Override
    public List<Genre> getGenres() {
        List<Genre> genres = new ArrayList<>();
        SqlRowSet genderRows = jdbcTemplate.queryForRowSet("select * from GENRES order by ID");
        while (genderRows.next()) {
            genres.add(makeGenre(genderRows));
        }
        return genres;
    }

    @Override
    public Boolean isGenreExist(Integer genreId) {
        return jdbcTemplate.queryForRowSet("select * from GENRES where ID = ?", genreId).next();
    }

    @Override
    public Genre makeGenre(SqlRowSet sqlRowSet) {
        return new Genre(
                sqlRowSet.getInt("id"),
                sqlRowSet.getString("genre")
        );
    }
}
