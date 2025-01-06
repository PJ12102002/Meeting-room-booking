package com.example.MeetingRoomBooking.repository;

import com.example.MeetingRoomBooking.model.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingRoomRepository extends JpaRepository<MeetingRoom,String> {
List<MeetingRoom> findByPhaseNoAndFloorNoAndAccess(Integer phaseNo,Integer floorNo,String access);
List<MeetingRoom> findByPhaseNoAndFloorNo(Integer phaseNo,Integer floorNo);
}
