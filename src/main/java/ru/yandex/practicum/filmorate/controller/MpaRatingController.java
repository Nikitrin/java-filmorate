package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.service.MpaRatingService;
import java.util.List;

@RestController
@RequestMapping(
        value = "/mpa",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class MpaRatingController {
    private final MpaRatingService mpaRatingService;

    @Autowired
    public MpaRatingController(MpaRatingService mpaRatingService) {
        this.mpaRatingService = mpaRatingService;
    }

    @GetMapping("/{id}")
    public MpaRating getMpaRatingById(@PathVariable Integer id) {
        return mpaRatingService.getMpaRatingById(id);
    }

    @GetMapping
    public List<MpaRating> getMpaRatings() {
        return mpaRatingService.getMpaRatings();
    }
}
