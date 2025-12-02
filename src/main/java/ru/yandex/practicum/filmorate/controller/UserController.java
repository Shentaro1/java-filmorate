package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.FriendsAddException;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.services.UserService;
import ru.yandex.practicum.filmorate.storages.user.InMemoryUserStorage;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/users")
@Slf4j
@AllArgsConstructor
public class UserController {
    UserService userService;
    InMemoryUserStorage inMemoryUserStorage;

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) throws ValidationException, FriendsAddException, NotFoundException {
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void dellFriend(@PathVariable int id, @PathVariable int friendId) throws ValidationException, FriendsAddException, NotFoundException {
        userService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getAllFriends(@PathVariable int id) throws ValidationException, FriendsAddException, NotFoundException {
        return userService.allFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public ArrayList<User> sharedListOfFriends(@PathVariable int id, @PathVariable int otherId) throws ValidationException, NotFoundException {
        return userService.sharedListFriends(id, otherId);
    }

    @PostMapping
    public User createUser(@RequestBody User user) throws ValidationException {
        return inMemoryUserStorage.createUser(user);
    }

    @PutMapping
    public User updateUser(@RequestBody User user) throws ValidationException, NotFoundException {
        return inMemoryUserStorage.updateUser(user);
    }

    @GetMapping
    public ArrayList<User> getAllUsers() throws ValidationException, NotFoundException {
        return inMemoryUserStorage.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) throws ValidationException, NotFoundException {
        return inMemoryUserStorage.getUser(id);
    }

    @DeleteMapping("/{id}")
    public void delUser(@PathVariable int id) throws ValidationException, NotFoundException {
        inMemoryUserStorage.delUser(id);
    }


}
