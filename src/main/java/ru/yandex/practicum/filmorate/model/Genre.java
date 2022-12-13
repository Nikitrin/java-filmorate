package ru.yandex.practicum.filmorate.model;

import lombok.*;

@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class Genre {
    private Integer id;
    private String name;
}
