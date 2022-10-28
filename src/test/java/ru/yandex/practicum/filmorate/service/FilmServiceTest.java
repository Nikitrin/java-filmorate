package ru.yandex.practicum.filmorate.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class FilmServiceTest {
    private final FilmStorage filmStorage = new InMemoryFilmStorage();
    private final UserStorage userStorage = new InMemoryUserStorage();
    private final FilmService filmService = new FilmService(filmStorage, userStorage);


    @Test
    void getFilmPopular() {
        Film goodNurse = filmStorage.saveFilm(new Film(null, null,
                "Good Nurse", "About a serial killer",
                LocalDate.of(2022, 9, 11), Duration.ofMinutes(121)));
        Film fall = filmStorage.saveFilm(new Film(null, null,
                "Fall", "About survival",
                LocalDate.of(2022, 8, 11), Duration.ofMinutes(107)));
        Film palmSprings = filmStorage.saveFilm(new Film(null, null,
                "Palm Springs", "About a time loop",
                LocalDate.of(2020, 7, 10), Duration.ofMinutes(90)));
        ArrayList<Film> popularFilms = new ArrayList<>();
        popularFilms.add(goodNurse);
        Assertions.assertEquals(new ArrayList<Film>(), filmService.getFilmPopular(0));
        Assertions.assertEquals(popularFilms, filmService.getFilmPopular(1));
        User Semen = userStorage.saveUser(new User(null, null,
                "semen@ya.ru", "$emenbI4", "Semen",
                LocalDate.of(1995, 2, 26)));
        User Svetlana = userStorage.saveUser(new User(null, null,
                "sveta.vasa@mail.ru", "milachka", "SvetlanaVasilievna",
                LocalDate.of(1986, 10, 4)));
        User Bob = userStorage.saveUser(new User(null, null,
                "bobina@gmail.com", "bob40000", "Bob",
                LocalDate.of(2003, 4, 17)));

        Assertions.assertEquals(1, Semen.getId());

    }

    @Test
    void addLikeToFilm() {
    }

    @Test
    void removeLikeFromFilm() {
    }
}