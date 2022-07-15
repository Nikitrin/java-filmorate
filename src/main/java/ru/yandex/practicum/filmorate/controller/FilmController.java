package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping(
        value = "/films",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class FilmController {
    Logger logger = LoggerFactory.getLogger(FilmController.class);

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public String saveFilmJson(@Valid @RequestBody Film film) {
        logger.info("Save user from json: {}", film);
        FilmService.saveFilm(film);
        return FilmService.filmToJson(film);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public String updateFilm(@Valid @RequestBody Film film) {
        logger.info("Update user from json: {}", film);
        FilmService.updateFilm(film);
        return FilmService.filmToJson(film);
    }

    @GetMapping()
    public String getFilms() {
        logger.info("Get all films");
        return FilmService.getFilms();
    }
}
