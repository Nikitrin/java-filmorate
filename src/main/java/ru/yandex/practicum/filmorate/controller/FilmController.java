package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import javax.validation.Valid;

@RestController
@RequestMapping(
        value = "/films",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class FilmController {
    private final FilmStorage filmStorage;
    private final Logger logger = LoggerFactory.getLogger(FilmController.class);
    @Autowired
    public FilmController(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public String saveFilmJson(@Valid @RequestBody Film film) {
        logger.info("Save user from json: {}", film);
        filmStorage.saveFilm(film);
        return filmStorage.filmToJson(film);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public String updateFilm(@Valid @RequestBody Film film) {
        logger.info("Update user from json: {}", film);
        filmStorage.updateFilm(film);
        return filmStorage.filmToJson(film);
    }

    @GetMapping()
    public String getFilms() {
        logger.info("Get all films");
        return filmStorage.getFilms();
    }
}
