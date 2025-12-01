package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.services.FilmService;


@RestController
@Slf4j
public class FilmController {
    FilmService filmService = new FilmService();

    @PutMapping("/films/{id}/like/{userId}")
    public void addLikes(@PathVariable int filmId, @PathVariable int userId) throws ValidationException, NotFoundException {
        filmService.addLike(filmId, userId);
    }

}
