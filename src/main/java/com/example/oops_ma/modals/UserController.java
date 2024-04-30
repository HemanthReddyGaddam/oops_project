package com.example.oops_ma.modals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody User user) {
        try {
            userService.createUser(user);
            return ResponseEntity.ok("Account Creation Successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Forbidden, Account already exists");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        return userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword())
                .map(user -> ResponseEntity.ok("Login Successful"))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username/Password Incorrect"));
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserDetail(@RequestParam("userID") Long userId) {
        Optional<User> user = userService.findUserById(userId);
        if (!user.isPresent()) {
            return ResponseEntity.badRequest().body("User does not exist");
        }
        User foundUser = user.get();
        return ResponseEntity.ok(new UserResponse(foundUser.getName(), foundUser.getId(), foundUser.getEmail()));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers().stream()
                .map(user -> new UserResponse(user.getName(), user.getId(), user.getEmail()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    // A helper class to format the response with only the required fields
    static class UserResponse {
        private String name;
        private Long userID;
        private String email;

        public UserResponse(String name, Long userID, String email) {
            this.name = name;
            this.userID = userID;
            this.email = email;
        }

        // Getters (and optionally setters)
        public String getName() {
            return name;
        }

        public Long getUserID() {
            return userID;
        }

        public String getEmail() {
            return email;
        }
    }
}