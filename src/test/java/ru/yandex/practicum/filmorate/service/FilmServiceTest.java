package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

class FilmServiceTest extends ServiceTest{
    @Test
    @DisplayName("Get a list of popular films")
    void getFilmPopular() {
        ArrayList<Film> popularFilms = new ArrayList<>();
        Assertions.assertEquals(popularFilms, filmService.getFilmPopular(0),
                "Get 0 popular movies");

        popularFilms.add(filmGoodNurse);
        Assertions.assertEquals(popularFilms, filmService.getFilmPopular(1),
                "If the films have an equal number of likes, then get film by smaller id");

        filmService.addLikeToFilm(2L, 1L);
        popularFilms.clear();
        popularFilms.add(filmFall);
        Assertions.assertEquals(popularFilms, filmService.getFilmPopular(1),
                "Get 1 popular film");

        filmService.addLikeToFilm(1L, 1L);
        popularFilms.clear();
        popularFilms.add(filmGoodNurse);
        popularFilms.add(filmFall);
        Assertions.assertEquals(popularFilms, filmService.getFilmPopular(2),
                "Get 2 popular films");

        popularFilms.add(filmPalmSprings);
        Assertions.assertEquals(popularFilms, filmService.getFilmPopular(10),
                "Get more films than saved in storage");
    }

    @Test
    @DisplayName("User likes film")
    void addLikeToFilm() {
        Film film = filmService.addLikeToFilm(1L, 1L);
        Set<Long> likes = new HashSet<>();
        likes.add(1L);
        Assertions.assertEquals(likes, film.getLikes(), "Checking the returned value with 1 like");
        Assertions.assertEquals(likes, filmStorage.getFilmById(1L).getLikes(),
                "Checking the saved value with 1 like");

        film = filmService.addLikeToFilm(1L, 3L);
        likes.add(3L);
        Assertions.assertEquals(likes, film.getLikes(), "Checking the returned value with 2 like");
        Assertions.assertEquals(likes, filmStorage.getFilmById(1L).getLikes(),
                "Checking the saved value with 2 like");

        Assertions.assertThrows(NotFoundException.class, () -> filmService.addLikeToFilm(10L, 1L),
                "Film was not found by id");
        Assertions.assertThrows(NotFoundException.class, () -> filmService.addLikeToFilm(1L, 10L),
                "User was not found by id");
        Assertions.assertThrows(ValidationException.class, () -> filmService.addLikeToFilm(1L, 1L),
                "User has already liked this film");
    }

    @Test
    @DisplayName("User removes like of film")
    void removeLikeFromFilm() {
        Assertions.assertThrows(NotFoundException.class, () -> filmService.removeLikeFromFilm(10L, 1L),
                "Film was not found by id");
        Assertions.assertThrows(NotFoundException.class, () -> filmService.removeLikeFromFilm(1L, 10L),
                "User was not found by id");
        Assertions.assertThrows(ValidationException.class, () -> filmService.removeLikeFromFilm(1L, 1L),
                "Film has not like from user");

        filmService.addLikeToFilm(2L, 2L);
        Film film = filmService.removeLikeFromFilm(2L, 2L);
        Set<Long> likes = new HashSet<>();
        Assertions.assertEquals(likes, film.getLikes(), "Checking the returned value with 0 like");
        Assertions.assertEquals(likes, filmStorage.getFilmById(2L).getLikes(),
                "Checking the saved value with 0 like");

        filmService.addLikeToFilm(2L, 1L);
        filmService.addLikeToFilm(2L, 3L);
        film = filmService.removeLikeFromFilm(2L, 1L);
        likes.add(3L);
        Assertions.assertEquals(likes, film.getLikes(), "Checking the returned value with 1 like");
        Assertions.assertEquals(likes, filmStorage.getFilmById(2L).getLikes(),
                "Checking the saved value with 1 like");
    }
}