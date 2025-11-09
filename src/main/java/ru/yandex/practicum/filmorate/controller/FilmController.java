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
        if (film == null) {
            throw new ValidationException("Передан пустой объект");
        } else if (film.getName().isEmpty()) {
            throw new ValidationException("Название не может быть пустым");
        } else if (film.getDescription().length() > 200) {
            throw new ValidationException("Максимальная длинна описания 200");
        } else if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12 ,28))) {
            throw new ValidationException("Дата релиза должна быть не раньше 28 декабря 1895 года");
        } else if (film.getDuration().isNegative()) {
            throw new ValidationException("продолжительность фильма должна быть положительным числом");
        } else {
            return filmStorage.put(film.getId(), film);
        }
    }

    @PutMapping("/update-film")
    public Film updateFilm(@RequestBody Film film) throws ValidationException {
        if (film == null) {
            throw new ValidationException("Передан пустой объект");
        } else if (film.getName() == null) {
            throw new ValidationException("Название не может быть пустым");
        } else if (film.getDescription().length() > 200) {
            throw new ValidationException("Максимальная длинна описания 200");
        } else if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12 ,28))) {
            throw new ValidationException("Дата релиза должна быть не раньше 28 декабря 1895 года");
        } else if (film.getDuration().isNegative()) {
            throw new ValidationException("Продолжительность фильма должна быть положительным числом");
        } else {
            filmStorage.remove(film.getId());
            return filmStorage.put(film.getId(), film);
        }
    }

    @GetMapping("/get-films")
    public void getFilmStorage() throws ValidationException {
        if (filmStorage.isEmpty()) {
            throw new ValidationException("Список filmStorage пуст");
        }
        for (Film film : filmStorage.values()) {
            System.out.println(film);
        }
    }
}
