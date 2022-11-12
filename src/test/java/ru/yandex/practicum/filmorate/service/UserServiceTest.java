package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class UserServiceTest extends ServiceTest{

    @Test
    @DisplayName("Add friend by id")
    void addFriendById() {
        List<User> friends = new ArrayList<>();
        friends.add(userSemen);
        friends.add(userSvetlana);
        Assertions.assertEquals(friends, userService.addFriendById(2L, 1L),
                "Checking the returned value with first friend");
        Set<Long> friendsSemen = new HashSet<>();
        friendsSemen.add(2L);
        Set<Long> friendsSvetlana = new HashSet<>();
        friendsSvetlana.add(1L);
        Assertions.assertEquals(friendsSemen, userSemen.getFriends(), "Checking saved one friend");
        Assertions.assertEquals(friendsSvetlana, userSvetlana.getFriends(), "Checking saved one friend");

        friends.clear();
        friends.add(userBob);
        friends.add(userSvetlana);
        Assertions.assertEquals(friends, userService.addFriendById(2L, 3L),
                "Checking the returned value with second friend");
        friendsSvetlana.add(3L);
        Assertions.assertEquals(friendsSvetlana, userSvetlana.getFriends(), "Checking saved two friends");

        Assertions.assertThrows(ValidationException.class, () -> userService.addFriendById(2L, 1L),
                "User has already been added to friends");
        Assertions.assertThrows(NotFoundException.class, () -> userService.addFriendById(10L, 1L),
                "User was not found by id");
        Assertions.assertThrows(NotFoundException.class, () -> userService.addFriendById(1L, 10L),
                "Friend was not found by id");
        Assertions.assertThrows(ValidationException.class, () -> userService.addFriendById(1L, 1L),
                "User can't add yourself as a friend");
    }

    @Test
    void getFriendsCommon() {
    }

    @Test
    void getFriends() {
    }

    @Test
    void removeFriendById() {
    }
}