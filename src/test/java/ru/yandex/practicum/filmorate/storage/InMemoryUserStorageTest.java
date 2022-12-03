package ru.yandex.practicum.filmorate.storage;

import org.junit.jupiter.api.BeforeEach;
import ru.yandex.practicum.filmorate.storage.inmemory.InMemoryUserStorage;

class InMemoryUserStorageTest extends UserStorageTest<InMemoryUserStorage> {

    @BeforeEach
    void setStorage() {
        userStorage = new InMemoryUserStorage();
    }
}