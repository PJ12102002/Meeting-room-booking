package com.example.MeetingRoomBooking.service;

import com.example.MeetingRoomBooking.model.*;
import com.example.MeetingRoomBooking.repository.AdminRepository;
import com.example.MeetingRoomBooking.repository.BookingRepository;
import com.example.MeetingRoomBooking.repository.ComplaintRepository;
import com.example.MeetingRoomBooking.repository.MeetingRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private MeetingRoomRepository meetingRoomRepository;
    @Autowired
    private ComplaintRepository complaintRepository;
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate l=LocalDate.now();
    String localDate=l.format(dateFormatter);
    LocalDate parsedDate = LocalDate.parse(localDate, dateFormatter);
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    LocalTime t= LocalTime.now();
    String localtime=t.format(timeFormatter);
    LocalTime parsedTime=LocalTime.parse(localtime,timeFormatter);
    public Admin findPassword(String zid){
        Optional<Admin> admin=adminRepository.findById(zid);
        return admin.get();
    }
    public List<Booking> currentHistory(){
        List<Booking> currentHistory = new ArrayList<>();
        List<Booking> bookings=bookingRepository.findBookingsWhereTodayIsBetweenStartAndEnd(parsedDate);
        for(Booking booking:bookings){
            if(parsedTime.isBefore(booking.getFromTime())){
                currentHistory.add(booking);
            }
        }
        return currentHistory;
    }
    public Admin profileUpdate(Admin admin,String zid){
        Optional<Admin> adm=adminRepository.findById(zid);

        if(adm.isPresent()){
            if((admin.getPath()!=null)&& (!admin.getPath().equals(""))) {
                adm.get().setPath(admin.getPath());
            }
            if((admin.getFullName() != null) && !admin.getFullName().equals("")){
            adm.get().setFullName(admin.getFullName());
            }
       return adminRepository.save(adm.get());
        }
        else return null;
    }

    public void addRoom(MeetingRoom meetingRoom){
        meetingRoomRepository.save(meetingRoom);
    }
    public MeetingRoom searchRoom(String roomNO){
        Optional<MeetingRoom> meetingRoom1=meetingRoomRepository.findById(roomNO);
        if(meetingRoom1.isEmpty())
            return null;
        else
            return  meetingRoom1.get();
    }
    public void removeRoom(MeetingRoom meetingRoom){

        meetingRoomRepository.delete(meetingRoom);
    }
    public List<MeetingRoom> roomFetch( Integer phase, Integer floor){

        List<MeetingRoom> meetingRooms = meetingRoomRepository.findByPhaseNoAndFloorNo(phase, floor);
        return meetingRooms;
    }
    public List<MeetingRoom> availableRoom( List<MeetingRoom> meetingRooms, LocalDate requestedStartDate,
                                           LocalTime requestedStartTime,
                                           LocalDate requestedEndDate, LocalTime requestedEndTime) {
        int flag=1;
        LocalDateTime existingStart = LocalDateTime.of(requestedStartDate, requestedStartTime);
        LocalDateTime existingEnd = LocalDateTime.of(requestedEndDate, requestedEndTime);
        List<Booking> bookings = bookingRepository.findBookingsWhereTodayIsBetweenStartAndEnd(requestedStartDate);
        if (bookings.isEmpty()) {
            meetingRooms.forEach(mr -> mr.setStatus("Available"));
        } else {
            for (MeetingRoom meetingRoom : meetingRooms) {
                for (Booking booking : bookings) {
                    if (meetingRoom.getRoomNo().equals( booking.getMeetRoom().getRoomNo())) {
                        LocalDateTime existingStart1 = LocalDateTime.of(booking.getStartDate(), booking.getFromTime());
                        LocalDateTime existingEnd2 = LocalDateTime.of(booking.getEndDate(), booking.getToTime());
                        if (existingStart1.isAfter(existingStart) && existingStart1.isAfter(existingEnd) && existingEnd2.isAfter(existingStart)
                                && existingEnd2.isAfter(existingEnd) && booking.getStatus().equals("Booked")) {
                            meetingRoom.setStatus("Available");
                        } else if (existingStart1.isBefore(existingStart) && existingStart1.isBefore(existingEnd) && existingEnd2.isBefore(existingStart)
                                && existingEnd2.isBefore(existingEnd) && booking.getStatus().equals("Booked")) {
                            meetingRoom.setStatus("Available");
                        } else {
                            meetingRoom.setStatus("Not Available");
                        }
                    }
                }
                if(flag==1){
                    meetingRoom.setStatus("Available");
                }
                flag=1;
            }
        }
        return meetingRooms;
    }
    public Booking booking(String zid,String roomNo,Booking booking){
        Optional<MeetingRoom> meetingRoom=meetingRoomRepository.findById(roomNo);
        booking.setZid(zid);
        booking.setMeetRoom(meetingRoom.get());
        return bookingRepository.save(booking);
    }
    public void complaintRegister(Complaint complaint){

        complaintRepository.save(complaint);
    }

    public List<Complaint> complaintResolve(Long compId){
        Optional<Complaint> comp=complaintRepository.findById(compId);
        if(comp.isPresent()){
            comp.get().setStatus(1);
            complaintRepository.save(comp.get());
        }
        return  complaintRepository.findByStatus(0);
    }
    public List<Complaint> complaintHistory(){
        return  complaintRepository.findByStatus(0);
    }
    public List<Booking> bookHistory(){
        List<Booking> bookingHistory = new ArrayList<>();
        List<Booking> book=bookingRepository.findBookingsWhereTodayIsGreaterThanEnd(parsedDate);
        for(Booking booking:book){
            if(parsedTime.isAfter(booking.getFromTime())){
                bookingHistory.add(booking);
            }
        }
        return  bookingHistory;
    }
}
