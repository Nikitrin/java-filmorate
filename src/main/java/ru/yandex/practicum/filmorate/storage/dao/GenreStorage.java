package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import ru.yandex.practicum.filmorate.model.Genre;
import java.util.List;

public interface GenreStorage {

    Genre getGenreById(Integer id);

    List<Genre> getGenres();

    Boolean isGenreExist(Integer genreId);

    Genre makeGenre(SqlRowSet sqlRowSet);
}
