package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.LikeAddException;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.services.FilmService;

import java.util.List;


@RestController
@RequestMapping("/films")
@Slf4j
@AllArgsConstructor
public class FilmController {
    FilmService filmService;

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
        return filmService.addFilm(film);
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) throws ValidationException, NotFoundException {
        return filmService.updateFilm(film);
    }

    @GetMapping
    public List<Film> getFilmStorage() throws ValidationException {
        return filmService.getFilmStorage();
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable int id) throws ValidationException {
        return filmService.getFilmById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteFilm(@PathVariable int id) throws ValidationException {
        filmService.deleteFilm(id);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(
            @RequestParam(value = "count", defaultValue = "10") int count) throws ValidationException, NotFoundException {
        return filmService.returnTopFilmsOnLike(count);
    }

}
