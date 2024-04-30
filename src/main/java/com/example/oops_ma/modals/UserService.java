package com.example.oops_ma.modals;

// UserService.java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> loginUser(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(user -> user.getPassword().equals(password));
    }

    public User createUser(User user) throws Exception {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new Exception("User already exists");
        }
        return userRepository.save(user);
    }

    public Optional<User> findUserById(Long userID) {
        return userRepository.findById(userID);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}