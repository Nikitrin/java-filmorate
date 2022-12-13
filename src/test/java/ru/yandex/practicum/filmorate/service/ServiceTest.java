//package ru.yandex.practicum.filmorate.service;
//
//import org.junit.jupiter.api.BeforeEach;
//import ru.yandex.practicum.filmorate.model.Film;
//import ru.yandex.practicum.filmorate.model.User;
//import ru.yandex.practicum.filmorate.storage.dao.FilmStorage;
//import ru.yandex.practicum.filmorate.storage.dao.FriendStorage;
//import ru.yandex.practicum.filmorate.storage.dao.impl.FriendDbStorage;
//import ru.yandex.practicum.filmorate.storage.inmemory.InMemoryFilmStorage;
//import ru.yandex.practicum.filmorate.storage.inmemory.InMemoryUserStorage;
//import ru.yandex.practicum.filmorate.storage.dao.UserStorage;
//import java.time.Duration;
//import java.time.LocalDate;
//
//public class ServiceTest {
//    protected FilmStorage filmStorage;
//    protected UserStorage userStorage;
//    protected FriendStorage friendStorage;
//    protected FilmService filmService;
//    protected UserService userService;
//    protected Film filmGoodNurse;
//    protected Film filmFall;
//    protected Film filmPalmSprings;
//    protected User userSemen;
//    protected User userSvetlana;
//    protected User userBob;
//
//    @BeforeEach
//    protected void setContext() {
//        filmStorage = new InMemoryFilmStorage();
//        userStorage = new InMemoryUserStorage();
//
//        filmService = new FilmService(filmStorage, userStorage);
//        //userService = new UserService(userStorage);
//
//        filmGoodNurse = new Film(null,  "Good Nurse", "About a serial killer",
//                LocalDate.of(2022, 9, 11), Duration.ofMinutes(121));
//        filmFall = new Film(null, "Fall", "About survival",
//                LocalDate.of(2022, 8, 11), Duration.ofMinutes(107));
//        filmPalmSprings = new Film(null, "Palm Springs", "About a time loop",
//                LocalDate.of(2020, 7, 10), Duration.ofMinutes(90));
//
//        filmStorage.saveFilm(filmGoodNurse);
//        filmStorage.saveFilm(filmFall);
//        filmStorage.saveFilm(filmPalmSprings);
//
//        userSemen = new User(null, "semen@ya.ru", "$emenbI4", "Semen",
//                LocalDate.of(1995, 2, 26));
//        userSvetlana = new User(null, "sveta.v@mail.ru", "milachka", "SvetVasilievna",
//                LocalDate.of(1986, 10, 4));
//        userBob = new User(null, "bobina@gmail.com", "bob40000", "Bob",
//                LocalDate.of(2003, 4, 17));
//
//        userStorage.saveUser(userSemen);
//        userStorage.saveUser(userSvetlana);
//        userStorage.saveUser(userBob);
//    }
//}
