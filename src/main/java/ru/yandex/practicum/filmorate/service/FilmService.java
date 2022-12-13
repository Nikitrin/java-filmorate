package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.dao.FilmLikesStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmLikesStorage filmLikesStorage;

    public List<Film> getFilmPopular(Integer count) {
        return filmLikesStorage.getFilmPopular(count);
    }

    public void addLikeToFilm(Integer id, Integer userId) {
        filmLikesStorage.addLikeToFilm(id, userId);
    }

    public void removeLikeFromFilm(Integer id, Integer userId) {
        filmLikesStorage.removeLikeFromFilm(id, userId);
    }
}
