package ru.yandex.practicum.filmorate.adapter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

class GsonFilmSerializeTest {

    @Test
    void filmToJson() {
        Film filmGoodNurse = new Film(1L, new HashSet<>(), "Good Nurse", "About a serial killer",
                LocalDate.of(2022, 9, 11), Duration.ofMinutes(121));
        String json = "{\n" +
                "  \"id\": 1,\n" +
                "  \"likes\": [],\n" +
                "  \"name\": \"Good Nurse\",\n" +
                "  \"description\": \"About a serial killer\",\n" +
                "  \"releaseDate\": \"2022-09-11\",\n" +
                "  \"duration\": 7260\n" +
                "}";
        Assertions.assertEquals(json, GsonFilmSerialize.toJson(filmGoodNurse), "Film to json");
    }

    @Test
    void listFilmsToJson() {
        Film filmGoodNurse = new Film(1L, new HashSet<>(), "Good Nurse", "About a serial killer",
                LocalDate.of(2022, 9, 11), Duration.ofMinutes(121));
        Film filmPalmSprings = new Film(2L, new HashSet<>(), "Palm Springs", "About a time loop",
                LocalDate.of(2020, 7, 10), Duration.ofMinutes(90));
        List<Film> films = new ArrayList<>();
        films.add(filmGoodNurse);
        films.add(filmPalmSprings);
        String json = "[\n" +
                "  {\n" +
                "    \"id\": 1,\n" +
                "    \"likes\": [],\n" +
                "    \"name\": \"Good Nurse\",\n" +
                "    \"description\": \"About a serial killer\",\n" +
                "    \"releaseDate\": \"2022-09-11\",\n" +
                "    \"duration\": 7260\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 2,\n" +
                "    \"likes\": [],\n" +
                "    \"name\": \"Palm Springs\",\n" +
                "    \"description\": \"About a time loop\",\n" +
                "    \"releaseDate\": \"2020-07-10\",\n" +
                "    \"duration\": 5400\n" +
                "  }\n" +
                "]";
        Assertions.assertEquals(json, GsonFilmSerialize.toJson(films), "List of films to json");
    }
}