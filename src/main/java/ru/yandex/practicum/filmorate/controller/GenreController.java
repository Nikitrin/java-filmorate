package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(
        value = "/genres",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class GenreController {
    private final GenreService genreService;
    private final Logger logger = LoggerFactory.getLogger(FilmController.class);

    @GetMapping("/{id}")
    public Genre getGenreById(@PathVariable Integer id) {
        logger.info("Get genre with id=" + id);
        return genreService.getGenreById(id);
    }

    @GetMapping
    public List<Genre> getGenres() {
        logger.info("Get all genres");
        return genreService.getGenres();
    }
}
