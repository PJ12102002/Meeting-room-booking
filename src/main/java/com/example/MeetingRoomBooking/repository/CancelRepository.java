package com.example.MeetingRoomBooking.repository;

import com.example.MeetingRoomBooking.model.Cancel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CancelRepository extends JpaRepository<Cancel,Long> {
}
