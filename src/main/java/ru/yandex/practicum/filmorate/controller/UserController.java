package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;

@RestController
@AllArgsConstructor
public class UserController {
    private HashMap<Integer, User> allUsers;

    @PostMapping("/create-user")
    public User createUser(@RequestBody User user) {

    }

    @PutMapping("/update-user")
    public User updateUser(@RequestBody User user) {

    }

    @GetMapping("/get-users")
    public void getAllUsers() {
        if (allUsers.isEmpty()){

        }
        for (User user : allUsers.values()) {
            System.out.println(user);
        }
    }
}
