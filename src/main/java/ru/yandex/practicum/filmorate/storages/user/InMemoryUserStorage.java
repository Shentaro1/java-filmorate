package ru.yandex.practicum.filmorate.storages.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.services.FilmService;
import ru.yandex.practicum.filmorate.services.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> allUsers = new HashMap<>();
    private int finalId = 0;

    public User createUser(User user) throws ValidationException {
        UserService.userValidator(user);
        user.setId(finalId + 1);
        finalId++;
        allUsers.put(user.getId(), user);
        log.trace("User добавлен: {}", user.getName());
        return user;

    }

    public User updateUser(User user) throws NotFoundException, ValidationException {
        UserService.userValidator(user);
        if (!allUsers.containsKey(user.getId())) {
            if (user.getId() < finalId) {
                user.setId(finalId + 1);
                createUser(user);
            } else if (user.getId() > finalId) {
                finalId = user.getId();
                createUser(user);
            }
            throw new NotFoundException("Ошибка при обновлении юзера");
        } else {
            allUsers.remove(user.getId());
            allUsers.put(user.getId(), user);
            log.info("User обновлён: {}", user.getName());
            return user;
        }
    }

    public ArrayList<User> getAllUsers() throws ValidationException, NotFoundException {
        if (allUsers.isEmpty()) {
            throw new NotFoundException("Список allUsers пуст");
        }
        return new ArrayList<>(allUsers.values());
    }

    public User getUser(int id) throws ValidationException, NotFoundException {
        if (allUsers.get(id) == null) {
            throw new NotFoundException("Юзера с таким id не существует");
        }
        return allUsers.get(id);
    }

    public void delUser(int id) throws ValidationException, NotFoundException {
        if (allUsers.get(id) == null) {
            throw new NotFoundException("Юзера с таким id не существует");
        }
        allUsers.remove(id);
    }

}
