package com.example.oops_ma.modals;

public class UserDTO {
    private String name;
    private Long userID;
    private String email;

    public UserDTO(String name, Long userID, String email) {
        this.name = name;
        this.userID = userID;
        this.email = email;
    }

    // Getters
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