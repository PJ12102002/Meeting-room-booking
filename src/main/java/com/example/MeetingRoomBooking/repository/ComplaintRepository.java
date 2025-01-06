package com.example.MeetingRoomBooking.repository;

import com.example.MeetingRoomBooking.model.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint,Long> {
    List<Complaint> findByStatus(Integer status);
}
