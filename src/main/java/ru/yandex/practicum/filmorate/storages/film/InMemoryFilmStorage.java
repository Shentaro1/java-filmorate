package ru.yandex.practicum.filmorate.storages.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.services.FilmService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> filmStorage = new HashMap<>();
    private int finalId = 0;

    public Film updateFilm(Film film) throws ValidationException, NotFoundException {
        FilmService.filmValidator(film);
        if (!filmStorage.containsKey(film.getId())) {
            throw new NotFoundException("Фильм с ID " + film.getId() + " не найден");
        }
        filmStorage.put(film.getId(), film);
        log.trace("Фильм обновлён: ID={}, Name={}", film.getId(), film.getName());
        return film;
    }

    public Film addFilm(Film film) throws ValidationException {
        film.setLikes(new HashSet<>());
        FilmService.filmValidator(film);
        film.setId(finalId + 1);
        finalId++;
        filmStorage.put(film.getId(), film);
        log.trace("Фильм добавлен: {}", film.getName());
        return film;

    }

    public ArrayList<Film> getFilmStorage() throws ValidationException {
        if (filmStorage.isEmpty()) {
            throw new ValidationException("Список filmStorage пуст");
        }
        return new ArrayList<>(filmStorage.values());
    }

    public Film getFilmById(int id) throws ValidationException {
        if (filmStorage.isEmpty()) {
            throw new ValidationException("Список filmStorage пуcт");
        }

        if (filmStorage.get(id) == null) {
            throw new ValidationException("Данного фильма не существует");
        }
        return filmStorage.get(id);
    }

    public void deleteFilm(int id) throws ValidationException {
        if (filmStorage.isEmpty()) {
            throw new ValidationException("Список filmStorage пуcт");
        }

        if (filmStorage.get(id) == null) {
            throw new ValidationException("Данного фильма не существует");
        }
        filmStorage.remove(id);
    }

}

