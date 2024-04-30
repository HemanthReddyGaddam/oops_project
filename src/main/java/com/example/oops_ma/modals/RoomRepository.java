package com.example.oops_ma.modals;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
//    List<Room> findByDateAndTimeAndCapacity(LocalDate date, LocalTime time, int capacity);

    boolean existsByRoomName(String roomName);

    Optional<Room> findById(Long id);
}