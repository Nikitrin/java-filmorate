package ru.yandex.practicum.filmorate.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

abstract class FilmStorageTest<T extends FilmStorage> {
    protected T filmStorage;
    protected Film filmGoodNurse;
    protected Film filmFall;
    protected Film filmPalmSprings;
    protected Film filmKnivesOut;
    protected Film filmTranscendence;

    @BeforeEach
    void setContext() {
        filmGoodNurse = new Film(null, null, "Good Nurse", "About a serial killer",
                LocalDate.of(2022, 9, 11), Duration.ofMinutes(121));
        filmFall = new Film(null, null, "Fall", "About survival",
                LocalDate.of(2022, 8, 11), Duration.ofMinutes(107));
        filmPalmSprings = new Film(null, null, "Palm Springs", "About a time loop",
                LocalDate.of(2020, 7, 10), Duration.ofMinutes(90));
        filmKnivesOut = new Film(null, null, "Knives Out", "Comedy detective film",
                LocalDate.of(2019, 11, 27), Duration.ofMinutes(130));
        filmTranscendence = new Film(null, null, "Transcendence", "Science fiction film",
                LocalDate.of(2014, 4, 18), Duration.ofMinutes(119));
    }

    @Test
    void saveFilm() {
        Film film = filmStorage.saveFilm(filmGoodNurse);
        Assertions.assertEquals(1L, film.getId(), "The film has been assigned an id");
        Assertions.assertEquals(new HashSet<Long>(), film.getLikes(), "Initialized field for likes");
        Assertions.assertEquals(film, filmStorage.getFilmById(1L), "Film saved in the storage");

        filmFall.setDuration(Duration.ofMinutes(-1));
        Assertions.assertThrows(ValidationException.class, () -> filmStorage.saveFilm(filmFall),
                "Duration can't be zero or negative");

        filmPalmSprings.setReleaseDate(LocalDate.of(1878, 10, 19));
        Assertions.assertThrows(ValidationException.class, () -> filmStorage.saveFilm(filmPalmSprings),
                "Release date can't be more than 1895-12-28");
    }

    @Test
    void updateFilm() {
        filmStorage.saveFilm(filmGoodNurse);
        Film film = new Film(1L, new HashSet<>(), "Bad Nurse", "About a crazy nurse",
                LocalDate.of(2000, 1, 1), Duration.ofMinutes(100));
        Assertions.assertEquals(film, filmStorage.updateFilm(film), "Check returned value");
        Assertions.assertEquals(film, filmStorage.getFilmById(1L), "Check saved value");

        film.setId(Long.MAX_VALUE);
        Assertions.assertThrows(NotFoundException.class, () -> filmStorage.updateFilm(film),
                "Film with id not find");
    }

    @Test
    void getFilms() {
        List<Film> films = new ArrayList<>();
        Assertions.assertEquals(films, filmStorage.getFilms(), "Empty list of film");

        films.add(filmPalmSprings);
        filmStorage.saveFilm(filmPalmSprings);
        Assertions.assertEquals(films, filmStorage.getFilms(), "List with one film");

        films.add(filmGoodNurse);
        films.add(filmFall);
        films.add(filmKnivesOut);
        films.add(filmTranscendence);

        filmStorage.saveFilm(filmGoodNurse);
        filmStorage.saveFilm(filmFall);
        filmStorage.saveFilm(filmKnivesOut);
        filmStorage.saveFilm(filmTranscendence);

        Assertions.assertEquals(films, filmStorage.getFilms(), "List with five films");
    }

    @Test
    void getFilmById() {
        Film saveFilmKnivesOut = filmStorage.saveFilm(filmKnivesOut);
        Film saveFilmTranscendence = filmStorage.saveFilm(filmTranscendence);
        Assertions.assertEquals(saveFilmKnivesOut, filmStorage.getFilmById(1L), "Get film by first id");
        Assertions.assertEquals(saveFilmTranscendence, filmStorage.getFilmById(2L), "Get film by second id");
        Assertions.assertThrows(NotFoundException.class, () -> filmStorage.getFilmById(Long.MAX_VALUE),
                "Film with id not find");
    }

    @Test
    void isFilmExist() {
        filmStorage.saveFilm(filmKnivesOut);
        Assertions.assertTrue(filmStorage.isFilmExist(1L), "Check film exist");
        Assertions.assertFalse(filmStorage.isFilmExist(Long.MAX_VALUE), "Check film no exist");
    }
}