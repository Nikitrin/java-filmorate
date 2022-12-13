//package ru.yandex.practicum.filmorate.service;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import ru.yandex.practicum.filmorate.exception.NotFoundException;
//import ru.yandex.practicum.filmorate.exception.ValidationException;
//import ru.yandex.practicum.filmorate.model.User;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//class UserServiceTest extends ServiceTest{
//
//    @Test
//    @DisplayName("Add friend by id")
//    void addFriendById() {
//        List<User> friends = new ArrayList<>();
//        friends.add(userSemen);
//        friends.add(userSvetlana);
//      //  Assertions.assertEquals(friends, userService.addFriendById(2, 1),
//    //            "Checking the returned value with first friend");
//        Set<Long> friendsSemen = new HashSet<>();
//        friendsSemen.add(2L);
//        Set<Long> friendsSvetlana = new HashSet<>();
//        friendsSvetlana.add(1L);
//     //   Assertions.assertEquals(friendsSemen, userSemen.getFriends(), "Checking saved one friend");
//    //    Assertions.assertEquals(friendsSvetlana, userSvetlana.getFriends(), "Checking saved one friend");
//
//        friends.clear();
//        friends.add(userBob);
//        friends.add(userSvetlana);
//    //    Assertions.assertEquals(friends, userService.addFriendById(2, 3),
//    //            "Checking the returned value with second friend");
//        friendsSvetlana.add(3L);
//      //  Assertions.assertEquals(friendsSvetlana, userSvetlana.getFriends(), "Checking saved two friends");
//
//        Assertions.assertThrows(ValidationException.class, () -> userService.addFriendById(2, 1),
//                "User has already been added to friends");
//        Assertions.assertThrows(NotFoundException.class, () -> userService.addFriendById(Integer.MAX_VALUE, 1),
//                "User was not found by id");
//        Assertions.assertThrows(NotFoundException.class, () -> userService.addFriendById(1, Integer.MAX_VALUE),
//                "Friend was not found by id");
//        Assertions.assertThrows(ValidationException.class, () -> userService.addFriendById(1, 1),
//                "User can't add yourself as a friend");
//    }
//
//    @Test
//    void getFriendsCommon() {
//        List<User> friendsCommon = new ArrayList<>();
//        Assertions.assertEquals(friendsCommon, userService.getFriendsCommon(1, 2),
//                "No common friends");
//        userService.addFriendById(2, 3);
//        userService.addFriendById(2, 1);
//        Assertions.assertEquals(friendsCommon, userService.getFriendsCommon(1, 2),
//                "No common friends");
//        userService.addFriendById(1, 3);
//        friendsCommon.add(userBob);
//        Assertions.assertEquals(friendsCommon, userService.getFriendsCommon(1, 2),
//                "One common friends");
//
//        Assertions.assertThrows(NotFoundException.class, () -> userService.getFriendsCommon(Integer.MAX_VALUE, 1),
//                "User was not found by id");
//        Assertions.assertThrows(NotFoundException.class, () -> userService.getFriendsCommon(1, Integer.MAX_VALUE),
//                "User was not found by id");
//    }
//
//    @Test
//    void getFriends() {
//        List<User> friends = new ArrayList<>();
//        Assertions.assertEquals(friends, userService.getFriends(3), "No friends");
//
//        userService.addFriendById(3, 1);
//        friends.add(userSemen);
//        Assertions.assertEquals(friends, userService.getFriends(3), "One friend");
//
//        userService.addFriendById(3, 2);
//        friends.add(userSvetlana);
//        Assertions.assertEquals(friends, userService.getFriends(3), "Two friends");
//
//        Assertions.assertThrows(NotFoundException.class, () -> userService.getFriends(Integer.MAX_VALUE),
//                "User was not found by id");
//    }
//
//    @Test
//    void removeFriendById() {
//        List<User> friends = new ArrayList<>();
//        Assertions.assertThrows(ValidationException.class, () -> userService.removeFriendById(1, 2),
//                "User does not have friend");
//        userService.addFriendById(1, 2);
//        userService.addFriendById(1, 3);
//        friends.add(userSemen);
//        friends.add(userBob);
//       // Assertions.assertEquals(friends, userService.removeFriendById(1, 3),
//  //              "Remove one friend, check returned value");
//        friends.clear();
//        friends.add(userSvetlana);
//        Assertions.assertEquals(friends, userService.getFriends(1),
//                "Remove one friend, check saved value");
//        friends.clear();
//        Assertions.assertEquals(friends, userService.getFriends(3),
//                "Remove one friend, check saved value");
//
//        friends.clear();
//        userService.removeFriendById(1, 2);
//        Assertions.assertEquals(friends, userService.getFriends(1), "Remove all friends");
//
//        Assertions.assertThrows(NotFoundException.class, () -> userService.removeFriendById(1, Integer.MAX_VALUE),
//                "User was not found by id");
//        Assertions.assertThrows(NotFoundException.class, () -> userService.removeFriendById(Integer.MAX_VALUE, 1),
//                "User was not found by id");
//        Assertions.assertThrows(ValidationException.class, () -> userService.removeFriendById(2, 1),
//                "User does not have friend");
//    }
//}