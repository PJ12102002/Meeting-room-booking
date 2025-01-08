package com.example.MeetingRoomBooking.service;

import com.example.MeetingRoomBooking.model.Booking;
import com.example.MeetingRoomBooking.model.Complaint;
import com.example.MeetingRoomBooking.model.Employee;
import com.example.MeetingRoomBooking.model.MeetingRoom;
import com.example.MeetingRoomBooking.repository.BookingRepository;
import com.example.MeetingRoomBooking.repository.ComplaintRepository;
import com.example.MeetingRoomBooking.repository.EmployeeRepository;
import com.example.MeetingRoomBooking.repository.MeetingRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UserService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private MeetingRoomRepository meetingRoomRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ComplaintRepository complaintRepository;
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate l = LocalDate.now();
    String localDate = l.format(dateFormatter);
    LocalDate parsedDate = LocalDate.parse(localDate, dateFormatter);
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    LocalTime t = LocalTime.now();
    String localtime = t.format(timeFormatter);
    LocalTime parsedTime = LocalTime.parse(localtime, timeFormatter);
    public Employee employeeDetails(String ZID) {
        Optional<Employee> employee = employeeRepository.findById(ZID);
        return employee.get();
    }


    public Employee signUser(String zid, String password) {
        Optional<Employee> employee = employeeRepository.findById(zid);
        if (employee.isPresent())
            return null;
        Employee e = new Employee();
        e.setZid(zid);
        e.setPass(password);
        return employeeRepository.save(e);
    }

    //
    public Employee profile(Employee employee, String zid) {
        Optional<Employee> emp = employeeRepository.findById(zid);
        if (emp.isPresent()) {

            if ((employee.getFullName() != null) && !employee.getFullName().equals("")) {
                emp.get().setFullName(employee.getFullName());
            }
            if ((employee.getDesignation() != null) && !(employee.getDesignation().equals(""))) {
                emp.get().setDesignation(employee.getDesignation());
            }
            if ((employee.getPart() != null) && !employee.getPart().equals("")) {
                emp.get().setPart(employee.getPart());
            }
            if ((employee.getRole() != null) && !employee.getRole().equals("")) {
                emp.get().setRole(employee.getRole());
            }
            if ((emp.get().getEmail() == null))
                emp.get().setEmail(employee.getEmail());
            return employeeRepository.save(emp.get());
        } else return null;
    }

    public Employee getEmployeeProfile(String zid) {
        return employeeRepository.findById(zid).orElse(null);
    }

    public String saveFile(MultipartFile file) throws Exception {

        String fileName = file.getOriginalFilename();
        Path uploadPath = Paths.get("uploads/");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return filePath.toString();
        } catch (Exception e) {
            throw new Exception("Could not save file: " + fileName, e);
        }
    }


    public List<MeetingRoom> roomFetch(String zid, Integer phase, Integer floor) {
        Employee employee = employeeDetails(zid);
        String access = employee.getPart();
        List<MeetingRoom> meetingRooms = meetingRoomRepository.findByPhaseNoAndFloorNoAndAccess(phase, floor, access);
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
    public Booking booking(String zid, String roomNo, Booking booking) {
        Optional<MeetingRoom> meetingRoom = meetingRoomRepository.findById(roomNo);
        booking.setZid(zid);
        booking.setMeetRoom(meetingRoom.get());
        return bookingRepository.save(booking);
    }

    public void complaintRegister(Complaint complaint) {
        complaintRepository.save(complaint);
    }

    public List<Booking> bookHistory(String zid){
        List<Booking> bookingHistory = new ArrayList<>();
        List<Booking> book=bookingRepository.findBookingsWhereTodayIsGreaterThanEndAndZid(parsedDate,zid);
        List<Booking> bookings=bookingRepository.findBookingsWhereTodayIsGreaterThanEndAndCanceledAndsZid(parsedDate,zid);
        for(Booking booking:book){
            if(parsedDate.equals(booking.getEndDate())&&parsedTime.isBefore(booking.getFromTime()) && booking.getStatus().equals("Booked")){
                continue;
            }
            bookingHistory.add(booking);
        }
        bookingHistory.addAll(bookings);
        return  bookingHistory;
    }
    public List<Booking> currentHistory(String zid){
        List<Booking> currentHistory = new ArrayList<>();
        List<Booking> bookings=bookingRepository.findBookingsWhereTodayIsBetweenStartAndEndAndZidAndBooked(parsedDate,zid);
        List<Booking> bookings1=bookingRepository.findBookingsWhereTodayIsGreaterThanStartAndZid(parsedDate,zid);
        if(bookings != null && !bookings.isEmpty()){
            for(Booking booking:bookings){
                if(parsedDate.equals(booking.getEndDate())&& parsedTime.isAfter(booking.getFromTime())){
                    continue;
                }
                currentHistory.add(booking);
            }}
        if(bookings1 != null && !bookings1.isEmpty()) {
            for (Booking booking : bookings1) {
                if (booking.getStatus().equals("Booked")) {
                    currentHistory.add(booking);
                }
            }
        }
        return currentHistory;
    }

    public Booking cancelRoom(Long book) {
        Optional<Booking> booking = bookingRepository.findById(book);
        if (booking.isEmpty()) {
            return null;

        } else {

            booking.get().setStatus("Canceled");

            return bookingRepository.save(booking.get());

        }
    }


}
























    //    public List<MeetingRoom> meetingRoomList(HttpSession session,Integer phno, Integer floorno){
//String ZId=String.valueOf(session.getAttribute("zid"));
//        Optional<Employee> emp=employeeRepository.findById(ZId
//        );
//
//        return meetingRoomRepository.findByPhaseNoAndFloorNoAndAccess(phno, floorno,emp.get().getPart());
//    }
//    public List<MeetingRoom> bookMeeting(HttpSession session, LocalDate date, LocalTime from, LocalTime to){
//        List<MeetingRoom> mrs= (List<MeetingRoom>) session.getAttribute("meetingList");
//        List<Booking> book=bookingRepository.findByDateOfBook(date);
//        if (book.isEmpty()) {
//            mrs.stream()
//                    .forEach(mr -> mr.setStatus("Available"));  // Update status for each meeting room
//        }
//        else{
//            for (MeetingRoom mr: mrs){
//                for(Booking booking:book){
//                    if(booking.getMeetRoom().getRoomId()==mr.getRoomId()){
//                        if(booking.getFromTime().equals(from) || booking.getToTime().equals(to)){
//                            mr.setStatus("Not Available");
//                        }
//                        else if(booking.getFromTime().isAfter(from) && booking.getToTime().isAfter(from) &&
//                        booking.getFromTime().isAfter(to) && booking.getToTime().isAfter(to))
//                        {
//                            mr.setStatus("Available");
//                        }
//                        else if(booking.getFromTime().isBefore(from) && booking.getToTime().isBefore(from) &&
//                                booking.getFromTime().isBefore(to) && booking.getToTime().isBefore(to))
//                        {
//                            mr.setStatus("Available");
//                        }
//                        else
//                        {
//                            mr.setStatus("Not Available");
//                        }
//                    }
//                }
//            }
//        }
//        return mrs;
//    }
//
//    public Booking booking(Booking booking, HttpSession session, Long roomId){
//        String zid= (String)session.getAttribute("zid");
//        LocalDate date= (LocalDate) session.getAttribute("date");
//        LocalTime from=(LocalTime) session.getAttribute("from");
//        LocalTime to=(LocalTime) session.getAttribute("to");
//        Optional<Employee> emp= employeeRepository.findById(zid);
//        Optional<MeetingRoom> meet=meetingRoomRepository.findById(roomId);
//        booking.setEmployee(emp.get());
//        booking.setMeetRoom(meet.get());
//        booking.setDateOfBook(date);
//        booking.setFromTime(from);
//        booking.setToTime(to);
//        return bookingRepository.save(booking);
//    }
////    public Login logincheck(String zid, String pass){
//////        Login login=null;
//////        Optional<Employee> employee=employeeRepository.findById(zid);
//////        System.out.println(employee.get().getRole());
//////        Optional<Login> logins=loginRepository.findById(zid);
//////        if(logins.isEmpty() && employee.isPresent()) {
//////            login=new Login();
//////            login.setZID(zid);
//////            login.setEmployee(employee.get());
//////            login.setPass(pass);
//////            loginRepository.save(login);
//////            return login;
//////        }
//////        else {
//////        return login;
//////        }
////    }
//public Login loginCheck(String zid, String pass) {
//    // Retrieve Employee based on ZID
//    Optional<Employee> employeeOpt = employeeRepository.findById(zid);
//    if (employeeOpt.isEmpty()) {
//        throw new RuntimeException("Employee with ZID " + zid + " not found.");  // Handle employee not found
//    }
//
//    // Retrieve existing Login for the given ZID
//    Optional<Login> existingLoginOpt = loginRepository.findById(zid);
//
//    if (existingLoginOpt.isEmpty()) {
//        // Create a new Login if none exists
//        Login login = new Login();
//        login.setZID(zid);
//        login.setEmployee(employeeOpt.get());  // Set the Employee for the login
//        login.setPass(pass);  // Set the password
//        loginRepository.save(login);  // Save the new Login
//        return login;
//    } else {
//        // If a Login exists, return the existing one
//        return existingLoginOpt.get();
//    }
//}



