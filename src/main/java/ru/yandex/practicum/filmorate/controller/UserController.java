package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.FriendsAddException;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.services.UserService;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/users")
@Slf4j
@AllArgsConstructor
public class UserController {
    UserService userService;

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
        return userService.createUser(user);
    }

    @PutMapping
    public User updateUser(@RequestBody User user) throws ValidationException, NotFoundException {
        return userService.updateUser(user);
    }

    @GetMapping
    public ArrayList<User> getAllUsers() throws ValidationException, NotFoundException {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) throws ValidationException, NotFoundException {
        return userService.getUser(id);
    }

    @DeleteMapping("/{id}")
    public void delUser(@PathVariable int id) throws ValidationException, NotFoundException {
        userService.delUser(id);
    }


}
