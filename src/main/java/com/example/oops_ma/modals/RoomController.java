package com.example.oops_ma.modals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RestController
public class RoomController {

    private RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

//    @GetMapping("/rooms")
//    public ResponseEntity<?> getRooms(@RequestParam LocalDate date, @RequestParam LocalTime time, @RequestParam int capacity) {
//        List<Room> rooms = roomService.findRoomsByFilter(date, time, capacity);
//        if (rooms.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No rooms found based on the filter");
//        }
//
//        return ResponseEntity.ok(rooms);
//    }

    @PostMapping("/rooms")
    public ResponseEntity<?> addRoom(@RequestBody Room room) {
        if (room.getRoomCapacity() == null || room.getRoomCapacity() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid capacity");
        }

        if (roomService.existsByRoomName(room.getRoomName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Room already exists");
        }

        roomService.save(room);
        return ResponseEntity.status(HttpStatus.CREATED).body("Room created successfully");
    }


    @PatchMapping("/rooms")
    public ResponseEntity<?> editRoom(@RequestBody Room room) {
        Optional<Room> existingRoom = roomService.findById(room.getId());
        if (!existingRoom.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room does not exist");
        }

        if (room.getRoomCapacity() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid capacity");
        }

        existingRoom.get().setRoomName(room.getRoomName());
        existingRoom.get().setRoomCapacity(room.getRoomCapacity());
        roomService.save(existingRoom.get());
        return ResponseEntity.ok("Room edited successfully");
    }

    @DeleteMapping("/rooms")
    public ResponseEntity<?> deleteRoom(@RequestBody Room room) {
        Optional<Room> existingRoom = roomService.findById(room.getId());
        if (!existingRoom.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room does not exist");
        }

        roomService.delete(existingRoom.get());
        return ResponseEntity.ok("Room deleted successfully");
    }
}