package ru.yandex.practicum.filmorate.storages.film;

import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;

public interface FilmStorage {
    Film updateFilm(Film film) throws ValidationException, NotFoundException;

    Film addFilm(Film film) throws ValidationException;

    ArrayList<Film> getFilmStorage() throws ValidationException, NotFoundException;

    Film getFilmById(int id) throws ValidationException;

    void deleteFilm(int id) throws ValidationException;
}
