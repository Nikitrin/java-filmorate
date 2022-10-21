package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class User {
    private Long id;
    @Email (message = "Invalid email")
    private String email;
    @NotNull (message = "Login can't be null")
    @NotBlank (message = "Login can't be blank")
    @Size(min = 1, max = 200, message = "Max length of login is 200 characters, min length is 1 character")
    @Pattern(regexp = "^\\S*$", message = "Login cannot contain spaces")
    private String login;
    private String name;
    @NotNull(message = "Birthday can't be null")
    @PastOrPresent (message = "Date can't be more than today")
    private LocalDate birthday;
}
