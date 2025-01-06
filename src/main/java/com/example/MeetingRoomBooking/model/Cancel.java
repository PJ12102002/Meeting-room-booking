package com.example.MeetingRoomBooking.model;

import jakarta.persistence.*;

@Entity
public class Cancel {
    @Id
    private Long cancelId;
    @OneToOne
    @JoinColumn(name="bookId", referencedColumnName = "bookId")
    private Booking booking;

    public Cancel() {
    }

    public Long getCancelId() {
        return cancelId;
    }

    public void setCancelId(Long cancelId) {
        this.cancelId = cancelId;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}
