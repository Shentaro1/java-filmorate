package ru.yandex.practicum.filmorate.interfaces;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

public interface Validator {

    boolean filmValidator(Film film) throws ValidationException;


    boolean userValidator(User user) throws ValidationException;
}
