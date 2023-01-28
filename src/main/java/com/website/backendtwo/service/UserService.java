package com.website.backendtwo.service;

import com.website.backendtwo.entity.User;
import com.website.backendtwo.exception.EmailAlreadyRegisteredException;
import com.website.backendtwo.exception.EmailNotRegisteredException;
import com.website.backendtwo.exception.InvalidCredentialException;
import com.website.backendtwo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public User registerUser(User user) {
        User existingUser = repository.findByEmail(user.getEmail()).orElse(null);
        if (existingUser != null) throw new EmailAlreadyRegisteredException();
        return repository.save(user);
    }

    public User loginUser(User user) {
        User existingUser = repository.findByEmail(user.getEmail()).orElse(null);
        if (existingUser == null)
            throw new EmailNotRegisteredException();
        if (!existingUser.getPassword().equals(user.getPassword()))
            throw new InvalidCredentialException();
        return existingUser;
    }

}
