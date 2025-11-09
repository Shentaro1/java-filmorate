package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.HashMap;

@RestController
@AllArgsConstructor
public class UserController {
    private HashMap<Integer, User> allUsers;

    @PostMapping("/create-user")
    public User createUser(@RequestBody User user) throws ValidationException {
        if (user == null) {
            throw new ValidationException("Передан пустой объект");
        } else if (user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @");
        } else if (user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        } else if (user.getName().isEmpty() && (user.getLogin().isEmpty() || user.getLogin().contains(" "))) {
            throw new ValidationException("Ошибка");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем");
        } else {
            return allUsers.put(user.getId(), user);
        }
    }

    @PutMapping("/update-user")
    public User updateUser(@RequestBody User user) throws ValidationException {
        if (user == null) {
            throw new ValidationException("Передан пустой объект");
        } else if (user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @");
        } else if (user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        } else if (user.getName().isEmpty() && (user.getLogin().isEmpty() || user.getLogin().contains(" "))) {
            throw new ValidationException("Ошибка");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем");
        } else {
            allUsers.remove(user.getId());
            return allUsers.put(user.getId(), user);
        }
    }

    @GetMapping("/get-users")
    public void getAllUsers() throws ValidationException {
        if (allUsers.isEmpty()){
            throw new ValidationException("Список allUsers пуст");
        }
        for (User user : allUsers.values()) {
            System.out.println(user);
        }
    }
}
