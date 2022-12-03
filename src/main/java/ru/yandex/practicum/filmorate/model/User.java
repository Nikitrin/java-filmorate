package ru.yandex.practicum.filmorate.model;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class User {
    @Expose
    private Integer id;
    @Expose
    @Email (message = "Invalid email")
    private String email;
    @Expose
    @NotNull (message = "Login can't be null")
    @NotBlank (message = "Login can't be blank")
    @Size(min = 1, max = 200, message = "Max length of login is 200 characters, min length is 1 character")
    @Pattern(regexp = "^\\S*$", message = "Login cannot contain spaces")
    private String login;
    @Expose
    private String name;
    @Expose
    @NotNull(message = "Birthday can't be null")
    @PastOrPresent (message = "Date can't be more than today")
    private LocalDate birthday;
}
