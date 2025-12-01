package ru.yandex.practicum.filmorate.storages.film;

import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;

public interface FilmStorage {
    Film updateFilm(Film film) throws ValidationException;
    Film addFilm(Film film) throws ValidationException;
    ArrayList<Film> getFilmStorage() throws ValidationException;
    Film getFilmById(int id) throws ValidationException;
    void deleteFilm(int id) throws ValidationException;
}
