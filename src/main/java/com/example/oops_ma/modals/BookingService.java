package com.example.oops_ma.modals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    public String createBooking(BookingDTO bookingDTO) {
        Optional<User> user = userRepository.findById(bookingDTO.getUserID());
        if (!user.isPresent()) {
            return "User does not exist";
        }

        Optional<Room> room = roomRepository.findById(bookingDTO.getRoomID());
        if (!room.isPresent()) {
            return "Room does not exist";
        }

        // Check if the date/time is in the past or invalid
        if (bookingDTO.getDateOfBooking().isBefore(LocalDate.now()) ||
                (bookingDTO.getDateOfBooking().isEqual(LocalDate.now()) && bookingDTO.getTimeFrom().isBefore(LocalTime.now())) ||
                bookingDTO.getTimeFrom().isAfter(bookingDTO.getTimeTo())) {
            return "Invalid date/time";
        }

        // Check if the room is available at the specified time
        boolean isAvailable = bookingRepository.findByRoomIdAndDateOfBookingAndTimeFromLessThanAndTimeToGreaterThan(
                bookingDTO.getRoomID(),
                bookingDTO.getDateOfBooking(),
                bookingDTO.getTimeFrom(),
                bookingDTO.getTimeTo()).isEmpty();

        if (!isAvailable) {
            return "Room unavailable";
        }

        // Create and save the new booking
        Booking newBooking = new Booking();
        newBooking.setUser(user.get());
        newBooking.setRoom(room.get());
        newBooking.setDateOfBooking(bookingDTO.getDateOfBooking());
        newBooking.setTimeFrom(bookingDTO.getTimeFrom());
        newBooking.setTimeTo(bookingDTO.getTimeTo());
        newBooking.setPurpose(bookingDTO.getPurpose());
        bookingRepository.save(newBooking);

        return "Booking created successfully";
    }

    public String editBooking(BookingEditDTO bookingDTO) {
        Optional<Booking> existingBooking = bookingRepository.findById(bookingDTO.getBookingID());

        Optional<User> user = userRepository.findById(bookingDTO.getUserID());
        if (!user.isPresent()) {
            return "User does not exist";
        }

        Optional<Room> room = roomRepository.findById(bookingDTO.getRoomID());
        if (!room.isPresent()) {
            return "Room does not exist";
        }

        if (!existingBooking.isPresent()) {
            return "Booking does not exist";
        }

        if (bookingDTO.getDateOfBooking().isBefore(LocalDate.now()) ||
                (bookingDTO.getDateOfBooking().isEqual(LocalDate.now()) && bookingDTO.getTimeFrom().isBefore(LocalTime.now())) ||
                bookingDTO.getTimeFrom().isAfter(bookingDTO.getTimeTo())) {
            return "Invalid date/time";
        }

        // Check if the room is available for the new times except for the current booking
        boolean isAvailable = bookingRepository.findByRoomIdAndDateOfBookingAndTimeFromLessThanAndTimeToGreaterThanAndIdNot(
                bookingDTO.getRoomID(),
                bookingDTO.getDateOfBooking(),
                bookingDTO.getTimeFrom(),
                bookingDTO.getTimeTo(),
                bookingDTO.getBookingID()).isEmpty();

        if (!isAvailable) {
            return "Room unavailable";
        }

        // Update the existing booking with the new details
        Booking booking = existingBooking.get();
        booking.setUser(user.get());
        booking.setRoom(room.get());
        booking.setDateOfBooking(bookingDTO.getDateOfBooking());
        booking.setTimeFrom(bookingDTO.getTimeFrom());
        booking.setTimeTo(bookingDTO.getTimeTo());
        booking.setPurpose(bookingDTO.getPurpose());
        bookingRepository.save(booking);

        return "Booking modified successfully";
    }

    public String deleteBooking(Long bookingID) {
        if (!bookingRepository.existsById(bookingID)) {
            return "Booking does not exist";
        }
        bookingRepository.deleteById(bookingID);
        return "Booking deleted successfully";
    }


    public List<Booking> findPastBookingsByUser(Optional<User> user) {
        if (!user.isPresent()) {
            return null;
        }
        User presentUser = user.get();
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
//        return bookingRepository.findByUserAndTimeToBefore(presentUser, currentTime);
        return bookingRepository.findPastBookingsByUser(presentUser, currentDate, currentTime);
    }

    public List<Booking> findUpcomingBookingsByUser(Optional<User> user) {
        if (!user.isPresent()) {
            return null;
        }
        User presentUser = user.get();
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        return bookingRepository.findUpcomingBookingsByUser(presentUser, currentDate, currentTime);

    }

//    public List<Booking> findUpcomingBookingsByUser(User user, LocalDateTime dateTime) {
//        // implementation
//        return bookingRepository.findByUserAndTimeFromAfter(user, dateTime);
//    }

}
