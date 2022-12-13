package ru.yandex.practicum.filmorate.storage.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.dao.MpaRatingStorage;

import java.util.ArrayList;
import java.util.List;

@Component
public class MpaRatingDbStorage implements MpaRatingStorage {
    private final JdbcTemplate jdbcTemplate;

    public MpaRatingDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public MpaRating getMpaRatingById(Integer id) {
        SqlRowSet mpaRatingRows = jdbcTemplate.queryForRowSet("select * from MPA_RATINGS where ID = ?", id);
        if (mpaRatingRows.next()) {
            return makeMpaRating(mpaRatingRows);
        } else {
            throw new NotFoundException(String.format("MpaRating with id=%s not found", id));
        }
    }

    @Override
    public List<MpaRating> getMpaRatings() {
        List<MpaRating> mpaRatings = new ArrayList<>();
        SqlRowSet genderRows = jdbcTemplate.queryForRowSet("select * from MPA_RATINGS order by ID");
        while (genderRows.next()) {
            mpaRatings.add(makeMpaRating(genderRows));
        }
        return mpaRatings;
    }

    private MpaRating makeMpaRating(SqlRowSet sqlRowSet) {
        return new MpaRating(
                sqlRowSet.getInt("id"),
                sqlRowSet.getString("rating")
        );
    }
}

