package ru.yandex.practicum.filmorate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exceptions.FriendsAddException;
import ru.yandex.practicum.filmorate.exceptions.LikeAddException;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse validationHandler(final ValidationException e) {
        return new ErrorResponse(
                "error: Ошибка в валидации",
                "errorMessage " + e.getMessage()
        );

    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notFoundHandler(final NotFoundException e) {
        return new ErrorResponse(
                "error: Объект не найден",
                "errorMessage " + e.getMessage()
        );
    }

    @ExceptionHandler(FriendsAddException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse friendAddHandler(final FriendsAddException e) {
        return new ErrorResponse(
                "error: Ошибка при добавлении друга",
                "errorMessage " + e.getMessage()
        );
    }

    @ExceptionHandler(LikeAddException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse likeAddHandler(final LikeAddException e) {
        return new ErrorResponse(
                "error: Ошибка при добавлении лайка",
                "errorMessage " + e.getMessage()
        );
    }
}
