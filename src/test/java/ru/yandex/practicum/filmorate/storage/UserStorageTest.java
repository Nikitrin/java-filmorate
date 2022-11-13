package ru.yandex.practicum.filmorate.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

abstract class UserStorageTest<T extends UserStorage> {
    protected T userStorage;
    protected User userSemen;
    protected User userSvetlana;
    protected User userBob;
    protected User userOleg;
    protected User userOlga;

    @BeforeEach
    void setContext() {
        userSemen = new User(null, null, "semen@ya.ru", "$emenbI4", "Semen",
                LocalDate.of(1995, 2, 26));
        userSvetlana = new User(null, null, "sveta.v@mail.ru", "milachka", "SvetVasilievna",
                LocalDate.of(1986, 10, 4));
        userBob = new User(null, null, "bobina@gmail.com", "bob40000", "Bob",
                LocalDate.of(2003, 4, 17));
        userOleg = new User(null, null, "oleg777@mail.ru", "oleg777", "Oleg",
                LocalDate.of(1984, 8, 3));
        userOlga = new User(null, null, "olga.sun@gmail.com", "sunny_girl", "Ol'chik",
                LocalDate.of(2002, 11, 9));
    }

    @Test
    void saveUser() {
        User user = userStorage.saveUser(userSemen);
        Assertions.assertEquals(1L, user.getId(), "The user has been assigned an id");
        Assertions.assertEquals(new HashSet<Long>(), user.getFriends(), "Initialized field for friends");
        Assertions.assertEquals(user, userStorage.getUserById(1L), "User saved in the storage");

        userSvetlana.setName(null);
        user = userStorage.saveUser(userSvetlana);
        Assertions.assertEquals(user.getLogin(), userStorage.getUserById(2L).getName(),
                "Save user without name");
    }

    @Test
    void updateUser() {
        userStorage.saveUser(userBob);
        User user = new User(1L, new HashSet<>(), "bobinator@gmail.com", "bob40001", "BoB",
                LocalDate.of(2004, 3, 21));
        Assertions.assertEquals(user, userStorage.updateUser(user), "Check returned value");
        Assertions.assertEquals(user, userStorage.getUserById(1L), "Check saved value");

        user.setId(Long.MAX_VALUE);
        Assertions.assertThrows(NotFoundException.class, () -> userStorage.updateUser(user),
                "Film with id not find");
    }

    @Test
    void getUsers() {
        List<User> users = new ArrayList<>();
        Assertions.assertEquals(users, userStorage.getUsers(), "Empty list of user");

        users.add(userSemen);
        userStorage.saveUser(userSemen);
        Assertions.assertEquals(users, userStorage.getUsers(), "List with one user");

        users.add(userSvetlana);
        users.add(userBob);
        users.add(userOleg);
        users.add(userOlga);

        userStorage.saveUser(userSvetlana);
        userStorage.saveUser(userBob);
        userStorage.saveUser(userOleg);
        userStorage.saveUser(userOlga);

        Assertions.assertEquals(users, userStorage.getUsers(), "List with five users");
    }

    @Test
    void isUserExist() {
        userStorage.saveUser(userOleg);
        Assertions.assertTrue(userStorage.isUserExist(1L), "Check user exist");
        Assertions.assertFalse(userStorage.isUserExist(Long.MAX_VALUE), "Check user no exist");
    }

    @Test
    void getUserById() {
        User saveUserOlga = userStorage.saveUser(userBob);
        User saveUserBob = userStorage.saveUser(userOlga);
        Assertions.assertEquals(saveUserOlga, userStorage.getUserById(1L), "Get user by first id");
        Assertions.assertEquals(saveUserBob, userStorage.getUserById(2L), "Get user by second id");
        Assertions.assertThrows(NotFoundException.class, () -> userStorage.getUserById(Long.MAX_VALUE),
                "User with id not find");
    }
}