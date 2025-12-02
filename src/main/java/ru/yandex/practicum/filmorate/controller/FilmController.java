package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.LikeAddException;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.services.FilmService;
import ru.yandex.practicum.filmorate.storages.film.InMemoryFilmStorage;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/films")
@Slf4j
@AllArgsConstructor
public class FilmController {
    FilmService filmService;
    InMemoryFilmStorage inMemoryFilmStorage;

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId) throws ValidationException, NotFoundException, LikeAddException {
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable int id, @PathVariable int userId) throws ValidationException, NotFoundException {
        filmService.deleteLike(id, userId);
    }

    @PostMapping
    public Film addFilm(@RequestBody Film film) throws ValidationException {
        return inMemoryFilmStorage.addFilm(film);
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) throws ValidationException, NotFoundException {
        return inMemoryFilmStorage.updateFilm(film);
    }

    @GetMapping
    public List<Film> getFilmStorage() {
        return inMemoryFilmStorage.getFilmStorage();
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable int id) throws ValidationException {
        return inMemoryFilmStorage.getFilmById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteFilm(@PathVariable int id) throws ValidationException {
        inMemoryFilmStorage.deleteFilm(id);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(
            @RequestParam(value = "count", defaultValue = "10") int count) throws LikeAddException, ValidationException, NotFoundException {
        return filmService.returnTopFilmsOnLike(count);
    }

}
