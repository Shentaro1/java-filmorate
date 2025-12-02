package ru.yandex.practicum.filmorate.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.filmorate.exceptions.LikeAddException;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storages.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storages.user.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class FilmService {
    InMemoryFilmStorage inMemoryFilmStorage;
    InMemoryUserStorage inMemoryUserStorage;

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
        if (film.getLikes() == null) {
            throw new ValidationException("Список лайков null");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12,28))) {
            throw new ValidationException("Дата релиза должна быть не раньше 28 декабря 1895 года");
        }
        if (film.getDuration() <= 0) {
            throw new ValidationException("Продолжительность фильма должна быть положительным числом");
        }
        return true;
    }

    public void addLike(int filmId, int userId) throws ValidationException, NotFoundException, LikeAddException {
        if (inMemoryUserStorage.getUser(userId) == null) {
            throw new NotFoundException("Пользователь с id=" + userId + " не найден");
        }
        if (inMemoryFilmStorage.getFilmById(filmId) == null) {
            throw new NotFoundException("Фильм с id=" + filmId + " не найден");
        }

        Film film = inMemoryFilmStorage.getFilmById(filmId);
        User user = inMemoryUserStorage.getUser(userId);

        Set<Long> likes = film.getLikes();
        if (likes == null) {
            likes = new HashSet<>();
            film.setLikes(likes);
        }

        long userIdLong = (long) userId;
        likes.add(userIdLong);

        film.setLikes(likes);
    }

    //пиздец
    public void deleteLike(int userId, int filmId) throws NotFoundException, ValidationException {
        Film film = inMemoryFilmStorage.getFilmById(filmId);
        if (film == null) {

        }

        User user = inMemoryUserStorage.getUser(userId);
        if (user == null) {
            throw new NotFoundException("Пользователь с id=" + userId + " не найден");
        }

        Set<Long> likes = film.getLikes();
        if (likes == null || likes.isEmpty()) {
            likes = new HashSet<>();
        }

        long userIdLong = (long) userId;
//        if (!likes.contains(userIdLong)) {
//            //throw new NotFoundException("Пользователь не ставил лайк этому фильму");
//        }

        likes.remove(userIdLong);

        log.info("Пользователь {} удалил лайк у фильма {}", userId, filmId);
    }

    public ArrayList<Film> returnTopFilmsOnLike(int count) throws LikeAddException, ValidationException, NotFoundException {
        ArrayList<Film> films = inMemoryFilmStorage.getFilmStorage();
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

}
