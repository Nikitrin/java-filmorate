package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.dao.MpaRatingStorage;
import java.util.List;

@Service
public class MpaRatingService {
    private final MpaRatingStorage mpaRatingStorage;

    @Autowired
    public MpaRatingService(MpaRatingStorage mpaRatingStorage) {
        this.mpaRatingStorage = mpaRatingStorage;
    }

    public MpaRating getMpaRatingById(Integer id) {
        return mpaRatingStorage.getMpaRatingById(id);
    }

    public List<MpaRating> getMpaRatings() {
        return mpaRatingStorage.getMpaRatings();
    }
}
