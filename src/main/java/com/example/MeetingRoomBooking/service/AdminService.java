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
        return adminRepository.findById(zid).orElse(null);
    }

    public Admin profileUpdate(Admin admin,String zid){
        Optional<Admin> adm=adminRepository.findById(zid);

        if(adm.isPresent()){
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
    public List<MeetingRoom> availableRoom(List<MeetingRoom> meetingRooms, LocalDate requestedStartDate,
                                           LocalTime requestedStartTime,
                                           LocalDate requestedEndDate, LocalTime requestedEndTime) {
        int flag = 1;
        LocalDateTime existingStart = LocalDateTime.of(requestedStartDate, requestedStartTime);
        LocalDateTime existingEnd = LocalDateTime.of(requestedEndDate, requestedEndTime);
        List<Booking> bookings = bookingRepository.findBookingsWhereTodayIsBetweenStartAndEnd(requestedStartDate);
        if (bookings.isEmpty()) {
            meetingRooms.forEach(mr -> mr.setStatus("Available"));
        } else {
            for (MeetingRoom meetingRoom : meetingRooms) {
                for (Booking booking : bookings) {
                    if (meetingRoom.getRoomNo().equals(booking.getMeetRoom().getRoomNo())) {
                        flag = 0;
                        LocalDateTime existingStart1 = LocalDateTime.of(booking.getStartDate(), booking.getFromTime());
                        LocalDateTime existingEnd2 = LocalDateTime.of(booking.getEndDate(), booking.getToTime());
                        if (existingStart1.isAfter(existingStart) && existingStart1.isAfter(existingEnd) && existingEnd2.isAfter(existingStart)
                                && existingEnd2.isAfter(existingEnd) && booking.getStatus().equals("Booked")) {
                            meetingRoom.setStatus("Available");
                        } else if (existingStart1.isBefore(existingStart) && existingStart1.isBefore(existingEnd) && existingEnd2.isBefore(existingStart)
                                && existingEnd2.isBefore(existingEnd) && booking.getStatus().equals("Booked")) {
                            meetingRoom.setStatus("Available");
                        } else if( booking.getStatus().equals("Canceled")) {
                            meetingRoom.setStatus("Available");
                        }
                        else{
                            meetingRoom.setStatus("Not Available");
                        }
                    }
                }
                if (flag == 1) {
                    meetingRoom.setStatus("Available");
                }
                flag = 1;
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


    public Booking cancelRoom(Long book){

        Optional<Booking> booking=bookingRepository.findById(book);

        if(booking.isEmpty()){

            return null;

        }

        else{

            booking.get().setStatus("Canceled");

            return  bookingRepository.save(booking.get());

        }

    }

    public List<Booking> allBookHistory(){

        List<Booking> book=bookingRepository.findBookingsWhereTodayIsGreaterThanEnd(parsedDate);

        List<Booking> bookings=bookingRepository.findBookingsWhereTodayIsGreaterThanEndAndCanceled(parsedDate);

        if(book.isEmpty())

            return null;

        return  bookHistory(book,bookings);

    }

    public List<Booking> adminBookHistory(String zid){
        List<Booking> book=bookingRepository.findBookingsWhereTodayIsGreaterThanEndAndZid(parsedDate,zid);
        List<Booking> bookings=bookingRepository.findBookingsWhereTodayIsGreaterThanEndAndCanceledAndsZid(parsedDate,zid);

        if(book.isEmpty())

            return null;

        return  bookHistory(book,bookings);

    }

    public List<Booking> bookHistory(List<Booking> book,List<Booking> bookings){

        List<Booking> bookingHistory = new ArrayList<>();

        for(Booking booking:book){

            if(parsedDate.equals(booking.getEndDate())&&parsedTime.isBefore(booking.getFromTime())&& booking.getStatus().equals("Booked")){

                continue;

            }

            bookingHistory.add(booking);

        }

        bookingHistory.addAll(bookings);

        if(bookingHistory.isEmpty())

            return null;

        return  bookingHistory;

    }

    public List<Booking> allCurrentHistory(){

        List<Booking> bookings=bookingRepository.findBookingsWhereTodayIsBetweenStartAndEndAndBooked(parsedDate);

        List<Booking> bookings1=bookingRepository.findBookingsWhereTodayIsGreaterThanStart(parsedDate);

        return  currentHistory(bookings,bookings1);

    }

    public List<Booking> adminCurrentHistory(String zid){

        List<Booking> bookings=bookingRepository.findBookingsWhereTodayIsBetweenStartAndEndAndZidAndBooked(parsedDate,zid);

        List<Booking> bookings1=bookingRepository.findBookingsWhereTodayIsGreaterThanStartAndZid(parsedDate,zid);

        return  currentHistory(bookings,bookings1);

    }

    public List<Booking> currentHistory(List<Booking>bookings, List<Booking> bookings1){

        List<Booking> currentHistory = new ArrayList<>();

        if(bookings != null && !bookings.isEmpty()) {

            for (Booking booking : bookings) {

                if (parsedDate.equals(booking.getEndDate()) && parsedTime.isAfter(booking.getFromTime())) {

                    continue;

                }

                currentHistory.add(booking);

            }

        }

        if(bookings1 != null && !bookings1.isEmpty()) {

            for (Booking booking : bookings1) {

                if (booking.getStatus().equals("Booked")) {

                    currentHistory.add(booking);

                }

            }

        }

        return currentHistory.isEmpty() ? new ArrayList<>() : currentHistory;

    }





}
