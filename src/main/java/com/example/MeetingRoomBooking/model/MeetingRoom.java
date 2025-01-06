package com.example.MeetingRoomBooking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;

import java.util.List;

@Entity
public class MeetingRoom {
    @Id
    private String roomNo;
    private String roomName;
    private Integer roomCapacity;
    private String access;
    private Integer  phaseNo;
    private Integer floorNo;
    @Transient
    private String status;
    @Transient
    @OneToMany(mappedBy = "meetRoom")
    private List<Booking> bookings;

    public MeetingRoom() {
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
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

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public Integer getPhaseNo() {
        return phaseNo;
    }

    public void setPhaseNo(Integer phaseNo) {
        this.phaseNo = phaseNo;
    }

    public Integer getFloorNo() {
        return floorNo;
    }

    public void setFloorNo(Integer floorNo) {
        this.floorNo = floorNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}
