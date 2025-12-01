package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class Film {
    @Builder.Default
    private int id = 0;
    @Builder.Default
    private String name = "";
    private String description;
    private LocalDate releaseDate;
    private int duration;
    @Builder.Default
    private Set<Long> likes = new HashSet<>();
}
