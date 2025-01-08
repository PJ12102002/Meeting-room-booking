package com.example.MeetingRoomBooking.repository;

import com.example.MeetingRoomBooking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Long> {

    @Query("SELECT b FROM Booking b WHERE b.startDate <= :today AND b.endDate >= :today AND b.status = 'Canceled'")
    List<Booking> findBookingsWhereTodayIsBetweenStartAndEnd(@Param("today") LocalDate today);

    @Query("SELECT b FROM Booking b WHERE b.startDate <= :today AND b.endDate >= :today AND b.status = 'Booked'")
    List<Booking> findBookingsWhereTodayIsBetweenStartAndEndAndBooked(@Param("today") LocalDate today);
    @Query("SELECT b FROM Booking b WHERE b.startDate <= :today AND b.endDate >= :today AND b.zid = :zid AND b.status ='Booked'")
    List<Booking> findBookingsWhereTodayIsBetweenStartAndEndAndZidAndBooked(@Param("today") LocalDate today, @Param("zid") String zid);
    @Query("SELECT b FROM Booking b WHERE b.startDate > :today ")
    List<Booking> findBookingsWhereTodayIsGreaterThanStart(@Param("today") LocalDate today);
    @Query("SELECT b FROM Booking b WHERE b.startDate <= :today AND b.endDate >= :today AND b.zid = :zid AND b.status = 'Canceled'")
    List<Booking> findBookingsWhereTodayIsBetweenStartAndEndAndZid(@Param("today") LocalDate today, @Param("zid") String zid);
    @Query("SELECT b FROM Booking b WHERE  b.endDate <= :today AND b.zid = :zid")
    List<Booking> findBookingsWhereTodayIsGreaterThanEndAndZid(@Param("today") LocalDate today, @Param("zid") String zid);
    @Query("SELECT b FROM Booking b WHERE  b.endDate <= :today ")
    List<Booking> findBookingsWhereTodayIsGreaterThanEnd(@Param("today") LocalDate today);
    @Query("SELECT b FROM Booking b WHERE b.startDate > :today AND b.zid = :zid")
    List<Booking> findBookingsWhereTodayIsGreaterThanStartAndZid(@Param("today") LocalDate today,@Param("zid") String zid);
    @Query("SELECT b FROM Booking b WHERE b.endDate > :today AND b.status = 'Canceled'")
    List<Booking> findBookingsWhereTodayIsGreaterThanEndAndCanceled(@Param("today") LocalDate today);
    @Query("SELECT b FROM Booking b WHERE b.startDate > :today AND b.zid = :zid AND b.status='Canceled'")
    List<Booking> findBookingsWhereTodayIsGreaterThanEndAndCanceledAndsZid(@Param("today") LocalDate today,@Param("zid") String zid);
    @Query("SELECT b FROM Booking b WHERE b.startDate = :today AND b.status = 'Booked' And b.fromTime =:time")
    List<Booking> findBookingsWhereTodayAndBooked(@Param("time") LocalTime time, @Param("today") LocalDate today);
}
