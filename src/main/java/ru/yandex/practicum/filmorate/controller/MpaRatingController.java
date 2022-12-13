package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.service.MpaRatingService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(
        value = "/mpa",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class MpaRatingController {
    private final MpaRatingService mpaRatingService;
    private final Logger logger = LoggerFactory.getLogger(FilmController.class);

    @GetMapping("/{id}")
    public MpaRating getMpaRatingById(@PathVariable Integer id) {
        logger.info("Get MPA rating with id=" + id);
        return mpaRatingService.getMpaRatingById(id);
    }

    @GetMapping
    public List<MpaRating> getMpaRatings() {
        logger.info("Get all MPA ratings");
        return mpaRatingService.getMpaRatings();
    }
}
