package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
public class User {
    private Long id;
    @Email (message = "Invalid email")
    private String email;
    @NotBlank (message = "Login can't be blank")
    private String login;
    private String name;
    @PastOrPresent (message = "Date can't be more than today")
    private LocalDate birthday;
}
