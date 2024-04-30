package com.example.oops_ma.modals;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class BookingController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookingService bookingService;

    @GetMapping("/history")
    public ResponseEntity<?> getBookingHistory(@RequestParam Long userID) {
        Optional<User> user = userService.findUserById(userID);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not exist");
        }

        List<Booking> bookings = bookingService.findPastBookingsByUser(user);
        if (bookings.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room does not exist");
        }

        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<?> getUpcomingBookings(@RequestParam Long userID) {
        Optional<User> user = userService.findUserById(userID);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not exist");
        }

        List<Booking> bookings = bookingService.findUpcomingBookingsByUser(user);
        if (bookings.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room does not exist");
        }

        return ResponseEntity.ok(bookings);
    }

    @PostMapping("/book")
    public ResponseEntity<String> createBooking(@RequestBody BookingDTO bookingDTO) {
        String result = bookingService.createBooking(bookingDTO);
        switch (result) {
            case "Booking created successfully":
                return ResponseEntity.ok(result);
            case "User does not exist":
            case "Room does not exist":
            case "Invalid date/time":
            case "Room unavailable":
                return ResponseEntity.badRequest().body(result);
            default:
                return ResponseEntity.internalServerError().body("An unexpected error occurred");
        }
    }

    @PatchMapping("/book")
    public ResponseEntity<String> editBooking(@RequestBody BookingEditDTO bookingDTO) {
        String result = bookingService.editBooking(bookingDTO);
        switch (result) {
            case "Booking modified successfully":
                return ResponseEntity.ok(result);
            case "User does not exist":
            case "Room does not exist":
            case "Invalid date/time":
            case "Room unavailable":
                return ResponseEntity.badRequest().body(result);
            default:
                return ResponseEntity.internalServerError().body("An unexpected error occurred");
        }
    }

    @DeleteMapping("/book")
    public ResponseEntity<String> deleteBooking(@RequestParam Long bookingID) {
        String result = bookingService.deleteBooking(bookingID);
        if ("Booking deleted successfully".equals(result)) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
}