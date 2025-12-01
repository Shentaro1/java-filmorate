package ru.yandex.practicum.filmorate.services;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storages.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storages.user.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.Set;

@Service
public class FilmService {
    InMemoryFilmStorage inMemoryFilmStorage = new InMemoryFilmStorage();
    InMemoryUserStorage inMemoryUserStorage = new InMemoryUserStorage();

    public static boolean filmValidator(Film film) throws ValidationException {
        if (film == null) {
            throw new ValidationException("Передан пустой объект");
        }
        if (film.getName() == null || film.getName().isBlank()) {
            throw new ValidationException("Название не может быть пустым");
        }
        if (film.getDescription() == null) {
            throw new ValidationException("Описание не может быть null");
        }
        if (film.getDescription().length() > 200) {
            throw new ValidationException("Максимальная длинна описания 200");
        }
        if (film.getReleaseDate() == null) {
            throw new ValidationException("Дата релиза не может быть null");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12,28))) {
            throw new ValidationException("Дата релиза должна быть не раньше 28 декабря 1895 года");
        }
        if (film.getDuration() <= 0) {
            throw new ValidationException("Продолжительность фильма должна быть положительным числом");
        }
        return true;
    }

    public void addLike(int filmId, int userId) throws ValidationException, NotFoundException {
        if (inMemoryUserStorage.getUser(userId) == null || inMemoryFilmStorage.getFilmById(filmId) == null) {
            throw new ValidationException("Невозможно выполнить действие");
        }

        Film film = inMemoryFilmStorage.getFilmById(filmId);
        User user = inMemoryUserStorage.getUser(userId);

        Set<Long> likes = film.getLikes();
        long userIdLong = (long) userId;
        if (likes.contains(userIdLong)) {
            throw new ValidationException("Пользователь уже поставил лайк этому фильму");
        }
        likes.add(userIdLong);

        film.setLikes(likes);
    }
}
