package ru.yandex.practicum.filmorate.storages.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.utils.Validator;

import java.util.*;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> filmStorage = new HashMap<>();
    private int finalId = 0;

    @Override
    public Film updateFilm(Film film) throws ValidationException, NotFoundException {
        if (film.getLikes() == null) {
            film.setLikes(new HashSet<>());
        }
        Validator.filmValidator(film);
        if (!filmStorage.containsKey(film.getId())) {
            throw new NotFoundException("Фильм с ID " + film.getId() + " не найден");
        }
        filmStorage.put(film.getId(), film);
        log.trace("Фильм обновлён: ID={}, Name={}", film.getId(), film.getName());
        return film;
    }

    @Override
    public Film addFilm(Film film) throws ValidationException {
        if (film.getLikes() == null) {
            film.setLikes(new HashSet<>());
        }
        Validator.filmValidator(film);
        film.setId(finalId + 1);
        finalId++;
        filmStorage.put(film.getId(), film);
        log.trace("Фильм добавлен: {}", film.getName());
        return film;

    }

    @Override
    public ArrayList<Film> getFilmStorage() {
        if (filmStorage.isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(filmStorage.values());
    }

    @Override
    public Film getFilmById(int id) throws ValidationException {
        if (id <= 0) {
            throw new ValidationException("ID должен быть положительным числом");
        }

        if (filmStorage == null) {
            throw new IllegalStateException("Хранилище фильмов не инициализировано");
        }

        Film film = filmStorage.get(id);
        if (film == null) {
            throw new NotFoundException("Фильм с id=" + id + " не найден");
        }

        return film;
    }

    @Override
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

