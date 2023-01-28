package com.website.backendtwo.service;

import com.website.backendtwo.entity.User;
import com.website.backendtwo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public User registerUser(User user) {
        User existingUser = repository.findByEmail(user.getEmail()).orElse(null);
        if (existingUser != null) return null;
        return repository.save(user);
    }

    public User loginUser(User user) {
        User existingUser = repository.findByEmail(user.getEmail()).orElse(null);
        if (existingUser == null) return null;
        if (existingUser.getPassword().equals(user.getPassword()))
            return existingUser;
        return null;
    }

}
