package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;


@RestController
@AllArgsConstructor
public class FilmController {
    private HashMap<Integer, Film> filmStorage;

    @PostMapping("/create-film")
    public Film addFilm(@RequestBody Film film) throws ValidationException {

    }

    @PutMapping("/update-film")
    public Film updateFilm(@RequestBody Film film) {

    }

    @GetMapping("/get-films")
    public void getFilmStorage() {
        if (filmStorage.isEmpty()) {

        }
        for (Film film : filmStorage.values()) {
            System.out.println(film);
        }
    }
}
