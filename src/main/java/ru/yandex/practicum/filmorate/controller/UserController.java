package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.execution.SpringValidator;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;


@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final HashMap<Integer, User> allUsers = new HashMap<>();
    private int finalId = 0;
    SpringValidator springValidator = new SpringValidator();

    @PostMapping
    public User createUser(@RequestBody User user) throws ValidationException {
        springValidator.userValidator(user);
        user.setId(finalId + 1);
        finalId++;
        allUsers.put(user.getId(), user);
        log.trace("User добавлен: {}", user.getName());
        return user;

    }

    @PutMapping
    public User updateUser(@RequestBody User user) throws ValidationException {
        springValidator.userValidator(user);
        if (!allUsers.containsKey(user.getId())) {
            if (user.getId() < finalId) {
                user.setId(finalId + 1);
                createUser(user);
            } else if (user.getId() > finalId) {
                finalId = user.getId();
                createUser(user);
            }
            throw new ValidationException("Ошибка");
        } else {
            allUsers.remove(user.getId());
            allUsers.put(user.getId(), user);
            log.trace("User обновлён: {}", user.getName());
            return user;
        }
    }

    @GetMapping
    public ArrayList<User> getAllUsers() throws ValidationException {
        if (allUsers.isEmpty()) {
            throw new ValidationException("Список allUsers пуст");
        }
        return new ArrayList<>(allUsers.values());
    }
}
