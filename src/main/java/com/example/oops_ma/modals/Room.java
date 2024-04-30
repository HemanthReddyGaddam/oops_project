package com.example.oops_ma.modals;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @JsonProperty("roomName")
    private String roomName;

    @Column(nullable = false)
    @JsonProperty("roomCapacity")
    private Integer roomCapacity;

    // Constructors
    public Room() {
        // Default constructor
    }

    public Room(String roomName, Integer roomCapacity) {
        this.roomName = roomName;
        this.roomCapacity = roomCapacity;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getRoomCapacity() {
        return roomCapacity;
    }

    public void setRoomCapacity(Integer roomCapacity) {
        this.roomCapacity = roomCapacity;
    }
}
