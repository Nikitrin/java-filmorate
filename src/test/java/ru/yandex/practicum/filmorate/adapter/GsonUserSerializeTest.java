package ru.yandex.practicum.filmorate.adapter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

class GsonUserSerializeTest {

    @Test
    void userToJson() {
        User userSvetlana = new User(1L, new HashSet<>(),
                "sveta.v@mail.ru", "milachka", "SvetVasilievna",
                LocalDate.of(1986, 10, 4));
        String json = "{\n" +
                "  \"id\": 1,\n" +
                "  \"email\": \"sveta.v@mail.ru\",\n" +
                "  \"login\": \"milachka\",\n" +
                "  \"name\": \"SvetVasilievna\",\n" +
                "  \"birthday\": \"1986-10-04\"\n" +
                "}";
        Assertions.assertEquals(json, GsonUserSerialize.toJson(userSvetlana), "User to json");
    }

    @Test
    void listOfUsersToJson() {
        User userSvetlana = new User(1L, new HashSet<>(),
                "sveta.v@mail.ru", "milachka", "SvetVasilievna",
                LocalDate.of(1986, 10, 4));
        User userBob = new User(2L, new HashSet<>(),
                "bobina@gmail.com", "bob40000", "Bob",
                LocalDate.of(2003, 4, 17));
        String json = "[\n" +
                "  {\n" +
                "    \"id\": 1,\n" +
                "    \"email\": \"sveta.v@mail.ru\",\n" +
                "    \"login\": \"milachka\",\n" +
                "    \"name\": \"SvetVasilievna\",\n" +
                "    \"birthday\": \"1986-10-04\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 2,\n" +
                "    \"email\": \"bobina@gmail.com\",\n" +
                "    \"login\": \"bob40000\",\n" +
                "    \"name\": \"Bob\",\n" +
                "    \"birthday\": \"2003-04-17\"\n" +
                "  }\n" +
                "]";
        List<User> users = new ArrayList<>();
        users.add(userSvetlana);
        users.add(userBob);
        Assertions.assertEquals(json, GsonUserSerialize.toJson(users), "List of users to json");
    }
}