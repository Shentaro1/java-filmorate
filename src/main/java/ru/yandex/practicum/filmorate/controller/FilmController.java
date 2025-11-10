package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.execution.SpringValidator;
import ru.yandex.practicum.filmorate.model.Film;


import java.util.ArrayList;
import java.util.HashMap;


@RestController
@Slf4j
public class FilmController {
    private final HashMap<Integer, Film> filmStorage = new HashMap<>();
    private int finalId = 0;
    SpringValidator springValidator = new SpringValidator();

    @PostMapping("/films")
    public Film addFilm(@RequestBody Film film) throws ValidationException {
        springValidator.filmValidator(film);
        film.setId(finalId + 1);
        finalId++;
        filmStorage.put(film.getId(), film);
        log.trace("Фильм добавлен: {}", film.getName());
        return film;

    }

    @PutMapping("/films")
    public Film updateFilm(@RequestBody Film film) throws ValidationException {
        springValidator.filmValidator(film);
        if (!filmStorage.containsKey(film.getId())) {
            throw new ValidationException("Фильм с ID " + film.getId() + " не найден");
        }
        filmStorage.put(film.getId(), film);
        log.trace("Фильм обновлён: ID={}, Name={}", film.getId(), film.getName());
        return film;
    }

    @GetMapping("/films")
    public ArrayList<Film> getFilmStorage() throws ValidationException {
        if (filmStorage.isEmpty()) {
            throw new ValidationException("Список filmStorage пуст");
        }
        return new ArrayList<>(filmStorage.values());
    }
}
