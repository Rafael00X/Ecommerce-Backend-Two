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
        return repository.save(user);
    }

}
