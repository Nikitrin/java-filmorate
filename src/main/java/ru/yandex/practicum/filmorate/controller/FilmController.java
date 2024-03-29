package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.adapter.GsonFilmSerialize;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.dao.FilmStorage;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(
        value = "/films",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class FilmController {
    private final FilmStorage filmStorage;
    private final FilmService filmService;
    private final Logger logger = LoggerFactory.getLogger(FilmController.class);

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public String saveFilmJson(@Valid @RequestBody Film film) {
        logger.info("Save film from json: {}", film);
        return GsonFilmSerialize.toJson(filmStorage.saveFilm(film));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public String updateFilm(@Valid @RequestBody Film film) {
        logger.info("Update film from json: {}", film);
        return GsonFilmSerialize.toJson(filmStorage.updateFilm(film));
    }

    @GetMapping()
    public String getFilms() {
        logger.info("Get all films");
        return GsonFilmSerialize.toJson(filmStorage.getFilms());
    }

    @GetMapping("/popular")
    public String getFilmPopular(@RequestParam(required = false, defaultValue = "10") Integer count) {
        logger.info("Get {} (default 10) popular films", count);
        return GsonFilmSerialize.toJson(filmService.getFilmPopular(count));
    }

    @GetMapping("/{id}")
    public String getFilmById(@PathVariable Integer id) {
        logger.info("Get film with id=" + id);
        return GsonFilmSerialize.toJson(filmStorage.getFilmById(id));
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLikeToFilm(@PathVariable Integer id, @PathVariable Integer userId) {
        logger.info("Add like to film with id=" + id);
        filmService.addLikeToFilm(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLikeFromFilm(@PathVariable Integer id, @PathVariable Integer userId) {
        logger.info("Remove user's like id={} from film with id={}", userId, id);
        filmService.removeLikeFromFilm(id, userId);
    }
}
