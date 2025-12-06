package ru.yandex.practicum.filmorate.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FriendsAddException;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storages.user.UserStorage;

import java.util.*;

@Service
@AllArgsConstructor
public class UserService {
    UserStorage userStorage;

    public void addFriend(int id, int friendId) throws FriendsAddException, ValidationException, NotFoundException {
        User user = userStorage.getUser(id);
        User friend = userStorage.getUser(friendId);

        if (user == null || friend == null) {
            throw new NotFoundException("Юзер не найден");
        }

        if (id == friendId) {
            throw new ValidationException("Нельзя добавить самого себя в друзья");
        }

        Set<Long> userFriends = user.getFriends();
        Set<Long> friendFriends = friend.getFriends();

        long friendIdLong = (long) friendId;
        if (userFriends.contains(friendIdLong)) {
            throw new FriendsAddException("Друг уже существует");
        }

        userFriends.add(friendIdLong);
        friendFriends.add((long) id);

        user.setFriends(userFriends);
        friend.setFriends(friendFriends);
    }

    public void deleteFriend(int id, int friendId) throws ValidationException, NotFoundException {
        User user = userStorage.getUser(id);
        if (userStorage.getUser(id) == null || userStorage.getUser(friendId) == null) {
            throw new NotFoundException("Юзер не найден");
        }
        Set<Long> set = user.getFriends();
        set.remove((long) friendId);
        user.setFriends(set);

        User friend = userStorage.getUser(friendId);
        Set<Long> setFriend = friend.getFriends();
        setFriend.remove((long) id);
        friend.setFriends(setFriend);
    }

    public List<User> allFriends(int id) throws ValidationException, FriendsAddException, NotFoundException {
        ArrayList<User> friends = new ArrayList<>();
        if (userStorage.getUser(id) == null) {
            throw new NotFoundException("Юзер не найден");
        }
        User user = userStorage.getUser(id);
        if (user.getFriends() == null || user.getFriends().isEmpty()) {
            return Collections.emptyList();
        }
        Set<Long> set = user.getFriends();
        for (Long i : set) {
            friends.add(userStorage.getUser(i.intValue()));
        }
        return friends;
    }

    public ArrayList<User> sharedListFriends(int id, int friendId) throws ValidationException, NotFoundException {
        ArrayList<User> users = new ArrayList<>();
        User user = userStorage.getUser(id);
        User friend = userStorage.getUser(friendId);

        Set<Long> userFriends = user.getFriends();
        Set<Long> friendFriends = friend.getFriends();

        Set<Long> intersection = new HashSet<>(userFriends);
        intersection.retainAll(friendFriends);
        for (Long i : intersection) {
            users.add(userStorage.getUser(i.intValue()));
        }
        return users;
    }

    public User createUser(User user) throws ValidationException {
        return userStorage.createUser(user);
    }

    public User updateUser(User user) throws NotFoundException, ValidationException {
        return userStorage.updateUser(user);
    }

    public ArrayList<User> getAllUsers() throws NotFoundException, ValidationException {
        return userStorage.getAllUsers();
    }

    public User getUser(int id) throws NotFoundException, ValidationException {
        return userStorage.getUser(id);
    }

    public void delUser(int id) throws NotFoundException, ValidationException {
        userStorage.delUser(id);
    }
}
