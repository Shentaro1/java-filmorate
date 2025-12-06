package ru.yandex.practicum.filmorate.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.LikeAddException;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storages.film.FilmStorage;
import ru.yandex.practicum.filmorate.storages.user.UserStorage;

import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class FilmService {
    FilmStorage filmStorage;
    UserStorage userStorage;

    public void addLike(int filmId, int userId) throws ValidationException, NotFoundException, LikeAddException {
        if (userStorage.getUser(userId) == null) {
            throw new NotFoundException("Пользователь с id=" + userId + " не найден");
        }
        if (filmStorage.getFilmById(filmId) == null) {
            throw new NotFoundException("Фильм с id=" + filmId + " не найден");
        }

        Film film = filmStorage.getFilmById(filmId);

        Set<Long> likes = film.getLikes();
        likes.add((long) userId);
    }

    public Film deleteLike(int filmId, int userId) throws NotFoundException, ValidationException {
        Film film = filmStorage.getFilmById(filmId);
        if (filmStorage.getFilmById(filmId) == null || userStorage.getUser(userId) == null) {
            throw new NotFoundException("Фильм не найден или юзер");
        }
        Set<Long> likes = film.getLikes();
        likes.remove((long) userId);
        return film;
    }

    public ArrayList<Film> returnTopFilmsOnLike(int count) throws NotFoundException, ValidationException {
        ArrayList<Film> films = filmStorage.getFilmStorage();
        if (films == null || films.isEmpty()) {
            return new ArrayList<>();
        }

        films.sort((f1, f2) -> {
            int likes1 = f1.getLikes() != null ? f1.getLikes().size() : 0;
            int likes2 = f2.getLikes() != null ? f2.getLikes().size() : 0;
            return Integer.compare(likes2, likes1);
        });
        int limit = Math.min(count, films.size());
        return new ArrayList<>(films.subList(0, limit));
    }

    public Film updateFilm(Film film) throws ValidationException, NotFoundException {
        return filmStorage.updateFilm(film);
    }

    public Film addFilm(Film film) throws ValidationException {
        return filmStorage.addFilm(film);
    }

    public ArrayList<Film> getFilmStorage() throws ValidationException {
        return filmStorage.getFilmStorage();
    }

    public Film getFilmById(int id) throws ValidationException {
        return filmStorage.getFilmById(id);
    }

    public void deleteFilm(int id) throws ValidationException {
        filmStorage.deleteFilm(id);
    }
}
