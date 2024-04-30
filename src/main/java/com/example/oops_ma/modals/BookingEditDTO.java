package com.example.oops_ma.modals;


import java.time.LocalDate;
import java.time.LocalTime;

public class BookingEditDTO {
    private Long userID;
    private Long roomID;
    private Long bookingID;
    private LocalDate dateOfBooking;
    private LocalTime timeFrom;
    private LocalTime timeTo;
    private String purpose;

    // Constructors
    public BookingEditDTO() {
    }

    public BookingEditDTO(Long userID, Long roomID, Long bookingID, LocalDate dateOfBooking,
                          LocalTime timeFrom, LocalTime timeTo, String purpose) {
        this.userID = userID;
        this.roomID = roomID;
        this.bookingID = bookingID;
        this.dateOfBooking = dateOfBooking;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.purpose = purpose;
    }

    // Getters and Setters
    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Long getRoomID() {
        return roomID;
    }

    public void setRoomID(Long roomID) {
        this.roomID = roomID;
    }

    public Long getBookingID() {
        return bookingID;
    }

    public void setBookingID(Long bookingID) {
        this.bookingID = bookingID;
    }

    public LocalDate getDateOfBooking() {
        return dateOfBooking;
    }

    public void setDateOfBooking(LocalDate dateOfBooking) {
        this.dateOfBooking = dateOfBooking;
    }

    public LocalTime getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(LocalTime timeFrom) {
        this.timeFrom = timeFrom;
    }

    public LocalTime getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(LocalTime timeTo) {
        this.timeTo = timeTo;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}