package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.model.MpaRating;

import java.util.List;

public interface MpaRatingStorage {

    MpaRating getMpaRatingById(Integer id);

    List<MpaRating> getMpaRatings();
}
