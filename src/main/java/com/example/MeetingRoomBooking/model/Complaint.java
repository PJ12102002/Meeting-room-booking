package com.example.MeetingRoomBooking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long compId;
    private String Zid;
    private Integer phaseNo;
    private Integer floorNo;
    private String roomNo;
    private String description;
    private Integer status;

    public Complaint() {
    }

    public Long getCompId() {
        return compId;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
    }

    public String getZid() {
        return Zid;
    }

    public void setZid(String zid) {
        Zid = zid;
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

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
