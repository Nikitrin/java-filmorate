package ru.yandex.practicum.filmorate.storage;

import org.junit.jupiter.api.BeforeEach;

class InMemoryUserStorageTest extends UserStorageTest<InMemoryUserStorage> {

    @BeforeEach
    void setStorage() {
        userStorage = new InMemoryUserStorage();
    }
}