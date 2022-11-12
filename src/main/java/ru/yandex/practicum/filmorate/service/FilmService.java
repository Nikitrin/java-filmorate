package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final TreeSet<Film> filmsPopular = new TreeSet<>(
            (o1, o2) -> {
                Integer likes1 = o1.getLikes().size();
                Integer likes2 = o2.getLikes().size();
                if (!likes1.equals(likes2)) {
                    return likes1.compareTo(likes2);
                }
                return o2.getId().compareTo(o1.getId());
            });

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public List<Film> getFilmPopular(Integer count) {
        filmsPopular.addAll(filmStorage.getFilms());
        ArrayList<Film> popularFilms = new ArrayList<>();
        int numberOfFilms = Objects.requireNonNullElse(count, 10);
        if (numberOfFilms > filmsPopular.size()) {
            numberOfFilms = filmsPopular.size();
        }
        for (int i = 0; i < numberOfFilms; i++) {
            popularFilms.add(filmsPopular.pollLast());
        }
        return popularFilms;
    }

    public Film addLikeToFilm(Long id, Long userId) {
        if (!filmStorage.isFilmExist(id)) {
            throw new NotFoundException(String.format("Film with id=%s not found", id));
        }
        if (!userStorage.isUserExists(userId)) {
            throw new NotFoundException(String.format("User with id=%s not found", userId));
        }
        if (filmStorage.getFilmsById(id).getLikes().contains(userId)) {
            throw new ValidationException(
                    String.format("User with id=%s has already liked film with id=%s", userId, id));
        }
        filmStorage.getFilmsById(id).getLikes().add(userId);
        return filmStorage.getFilmsById(id);
    }

    public Film removeLikeFromFilm(Long id, Long userId) {
        if (!filmStorage.isFilmExist(id)) {
            throw new NotFoundException(String.format("Film with id=%s not found", id));
        }
        if (!userStorage.isUserExists(userId)) {
            throw new NotFoundException(String.format("User with id=%s not found", userId));
        }
        if (!filmStorage.getFilmsById(id).getLikes().contains(userId)) {
            throw new ValidationException(
                    String.format("Film with id=%s has not like from user with id=%s", id, userId));
        }
        filmStorage.getFilmsById(id).getLikes().remove(userId);
        return filmStorage.getFilmsById(id);
    }
}
