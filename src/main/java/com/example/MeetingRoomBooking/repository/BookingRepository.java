package com.example.MeetingRoomBooking.repository;

import com.example.MeetingRoomBooking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Long> {

    @Query("SELECT b FROM Booking b WHERE b.startDate <= :today AND b.endDate >= :today ")
    List<Booking> findBookingsWhereTodayIsBetweenStartAndEnd(@Param("today") LocalDate today);
    @Query("SELECT b FROM Booking b WHERE b.startDate <= :today AND b.endDate >= :today AND b.id = :zid")
    List<Booking> findBookingsWhereTodayIsBetweenStartAndEndAndZid(@Param("today") LocalDate today, @Param("zid") String zid);
    @Query("SELECT b FROM Booking b WHERE  b.endDate <= :today AND b.id = :zid")
    List<Booking> findBookingsWhereTodayIsGreaterThanEndAndZid(@Param("today") LocalDate today, @Param("zid") String zid);
    @Query("SELECT b FROM Booking b WHERE  b.endDate <= :today ")
    List<Booking> findBookingsWhereTodayIsGreaterThanEnd(@Param("today") LocalDate today);
}
