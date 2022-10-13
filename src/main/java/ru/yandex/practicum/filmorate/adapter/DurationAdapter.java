package ru.yandex.practicum.filmorate.adapter;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DurationAdapter implements JsonSerializer<Duration> {

    public JsonElement serialize(Duration duration, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(duration.toSeconds());
    }
}
