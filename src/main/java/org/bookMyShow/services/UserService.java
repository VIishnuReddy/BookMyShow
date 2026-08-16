package org.bookMyShow.services;

import org.bookMyShow.entities.User;

import java.util.HashMap;
import java.util.Map;

public class UserService {

    private Map<Long,User> users = new HashMap<>();

    public User getUser(Long userId){
        return users.get(userId);
    }

    public void addUser(User user){
        users.put(user.getId(),user);
    }
}
