package com.example.oops_ma.modals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

//    public List<Room> findRoomsByFilter(LocalDate date, LocalTime time, int capacity) {
//        return roomRepository.findByDateAndTimeAndCapacity(date, time, capacity);
//    }

    public boolean existsByRoomName(String roomName) {
        return roomRepository.existsByRoomName(roomName);
    }

    public Room save(Room room) {
        return roomRepository.save(room);
    }

    public Optional<Room> findById(Long id) {
        return roomRepository.findById(id);
    }

    public void delete(Room room) {
        roomRepository.delete(room);
    }
}