package com.example.oops_ma.modals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

//    List<Booking> findByUserAndTimeToBefore(User user, LocalDateTime timeTo);
//    List<Booking> findByUserAndTimeToBefore(User user, LocalDateTime timeTo);
    @Query("SELECT b FROM Booking b WHERE b.user = :user AND (b.dateOfBooking < :currentDate OR (b.dateOfBooking = :currentDate AND b.timeTo < :currentTime))")
    List<Booking> findPastBookingsByUser(@Param("user") User user, @Param("currentDate") LocalDate currentDate, @Param("currentTime") LocalTime currentTime);


//    List<Booking> findByUserAndTimeFromAfter(User user, LocalTime timeFrom);
    @Query("SELECT b FROM Booking b WHERE b.user = :user AND (b.dateOfBooking > :currentDate OR (b.dateOfBooking = :currentDate AND b.timeFrom > :currentTime))")
    List<Booking> findUpcomingBookingsByUser(@Param("user") User user, @Param("currentDate") LocalDate currentDate, @Param("currentTime") LocalTime currentTime);



    List<Booking> findByRoomIdAndDateOfBookingAndTimeFromLessThanAndTimeToGreaterThanAndIdNot(
            Long roomId,
            LocalDate dateOfBooking,
            LocalTime timeFrom,
            LocalTime timeTo,
            Long idNot);

    @Query("SELECT b FROM Booking b WHERE b.room.id = :roomId AND b.dateOfBooking = :dateOfBooking AND " +
            "(b.timeFrom < :timeTo AND b.timeTo > :timeFrom)")
    List<Booking> findByRoomIdAndDateOfBookingAndTimeFromLessThanAndTimeToGreaterThan(
            @Param("roomId") Long roomId,
            @Param("dateOfBooking") LocalDate dateOfBooking,
            @Param("timeFrom") LocalTime timeFrom,
            @Param("timeTo") LocalTime timeTo);
}
