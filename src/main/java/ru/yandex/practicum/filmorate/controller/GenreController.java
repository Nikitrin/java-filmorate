package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;
import java.util.List;

@RestController
@RequestMapping(
        value = "/genres",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class GenreController {
    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/{id}")
    public Genre getGenreById(@PathVariable Integer id) {
        return genreService.getGenreById(id);
    }

    @GetMapping
    public List<Genre> getGenres() {
        return genreService.getGenres();
    }
}