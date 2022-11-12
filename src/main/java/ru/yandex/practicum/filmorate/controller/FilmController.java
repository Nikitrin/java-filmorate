package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.adapter.GsonFilmAdapter;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import javax.validation.Valid;

@RestController
@RequestMapping(
        value = "/films",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class FilmController {
    private final FilmStorage filmStorage;
    private final FilmService filmService;
    private final Logger logger = LoggerFactory.getLogger(FilmController.class);
    @Autowired
    public FilmController(FilmStorage filmStorage, FilmService filmService) {
        this.filmStorage = filmStorage;
        this.filmService = filmService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public String saveFilmJson(@Valid @RequestBody Film film) {
        logger.info("Save film from json: {}", film);
        return GsonFilmAdapter.toJson(filmStorage.saveFilm(film));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public String updateFilm(@Valid @RequestBody Film film) {
        logger.info("Update film from json: {}", film);
        return GsonFilmAdapter.toJson(filmStorage.updateFilm(film));
    }

    @GetMapping()
    public String getFilms() {
        logger.info("Get all films");
        return GsonFilmAdapter.toJson(filmStorage.getFilms());
    }

    @GetMapping("/popular")
    public String getFilmPopular(@RequestParam(required = false) Integer count) {
        logger.info("Get {} (default 10) popular films", count);
        return GsonFilmAdapter.toJson(filmService.getFilmPopular(count));
    }

    @GetMapping("/{id}")
    public String getFilmById(@PathVariable Long id) {
        logger.info("Get film with id=" + id);
        return GsonFilmAdapter.toJson(filmStorage.getFilmsById(id));
    }

    @PutMapping("/{id}/like/{userId}")
    public String addLikeToFilm(@PathVariable Long id, @PathVariable Long userId) {
        logger.info("Add like to film with id=" + id);
        return GsonFilmAdapter.toJson(filmService.addLikeToFilm(id, userId));
    }

    @DeleteMapping("/{id}/like/{userId}")
    public String removeLikeFromFilm(@PathVariable Long id, @PathVariable Long userId) {
        logger.info("Remove user's like id={} from film with id={}", userId, id);
        return GsonFilmAdapter.toJson(filmService.removeLikeFromFilm(id, userId));
    }
}
