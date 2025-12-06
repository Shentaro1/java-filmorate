package ru.yandex.practicum.filmorate.storages.user;

import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;

public interface UserStorage {
    User createUser(User user) throws ValidationException;

    User updateUser(User user) throws ValidationException, NotFoundException;

    ArrayList<User> getAllUsers() throws ValidationException, NotFoundException;

    User getUser(int id) throws ValidationException, NotFoundException;

    void delUser(int id) throws ValidationException, NotFoundException;
}
