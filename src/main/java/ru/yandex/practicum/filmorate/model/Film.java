package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import javax.validation.constraints.*;
import java.time.Duration;
import java.time.LocalDate;

@Data
public class Film {
    private Long id;
    @NotNull (message = "Name can't be null")
    @NotBlank (message = "Name can't be blank")
    @Size(min = 1, max = 200, message = "Max length of name is 200 characters, min length is 1 character")
    private String name;
    @NotNull (message = "Description can't be null")
    @NotBlank (message = "Description can't be blank")
    @Size(min = 1, max = 200, message = "Max length of description is 200 characters, min length is 1 character")
    private String description;
    @NotNull (message = "Release date can't be null")
    private LocalDate releaseDate;
    @NotNull (message = "Duration can't be null")
    private Duration duration;
}