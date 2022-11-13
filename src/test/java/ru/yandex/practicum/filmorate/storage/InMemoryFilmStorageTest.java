package ru.yandex.practicum.filmorate.storage;

import org.junit.jupiter.api.BeforeEach;

class InMemoryFilmStorageTest extends FilmStorageTest<InMemoryFilmStorage> {

    @BeforeEach
    void setStorage() {
        filmStorage = new InMemoryFilmStorage();
    }
}