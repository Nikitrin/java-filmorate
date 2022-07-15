package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.Duration;
import java.time.LocalDate;

@Data
public class Film {
    private Long id;
    @NotBlank (message = "Name can't be blank")
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Duration duration;
}
