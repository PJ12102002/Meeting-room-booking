package com.example.MeetingRoomBooking.controller;

import com.example.MeetingRoomBooking.model.Admin;
import com.example.MeetingRoomBooking.model.Booking;
import com.example.MeetingRoomBooking.model.Complaint;
import com.example.MeetingRoomBooking.model.MeetingRoom;
import com.example.MeetingRoomBooking.service.AdminService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
public class AdminController {
    @Autowired
    private AdminService adminService;
    HttpSession httpSession;
    @PostMapping("/login")
    public ResponseEntity<?> adminLogin(HttpSession httpSession, @RequestParam String zid, @RequestParam String password) {
        this.httpSession = httpSession;
        httpSession.setAttribute("zid", zid);
        Admin admin = adminService.findPassword(zid);
        if (admin == null) {
            return ResponseEntity.ok().body("Username or password is incorrect.");
        }
        if (!admin.getPassword().equals(password)) {
            return ResponseEntity.ok().body("Username or password is incorrect.");
        }
        return ResponseEntity.ok().body(admin);
    }



    @GetMapping("/currentHistory")
    public ResponseEntity<?> currentHistory(){
        List<Booking> bookings=adminService.currentHistory();
        if(bookings.isEmpty())
            return  ResponseEntity.ok().body("NO Booking List Available");
        else
            return ResponseEntity.ok().body(bookings);
    }
    @PostMapping("/profile")
    public ResponseEntity<?> profileUpdate(@RequestBody Admin admin) {
        String zid = (String) httpSession.getAttribute("zid");
        admin.setZid(zid);
        Admin adm=adminService.profileUpdate(admin,zid);
        if(adm==null){
            return ResponseEntity.ok().body("Unable to update");
        }
        else
            return ResponseEntity.ok().body("Updated successfully");
    }
    @PostMapping("/addRoom")
    public ResponseEntity<?> addRoom(@RequestBody MeetingRoom meetroom){
        MeetingRoom meetingRoom=adminService.searchRoom(meetroom.getRoomNo());
        if(meetingRoom!=null){
            return ResponseEntity.ok().body(new HashMap<String, String>() {{
                put("message", "No");
            }});
        }
        adminService.addRoom(meetroom);
        return ResponseEntity.ok().body(new HashMap<String, String>() {{
            put("message", "Yes");
        }});
    }
    @PostMapping("/removeRoom")
    public ResponseEntity<?> removeRoom(@RequestParam String roomNo){
        MeetingRoom meetingRoom=adminService.searchRoom(roomNo);
        if(meetingRoom==null){
            return ResponseEntity.ok().body(new HashMap<String, String>() {{
                put("message", "No");
            }});
        }
        adminService.removeRoom(meetingRoom);
        return ResponseEntity.ok().body(new HashMap<String, String>() {{
            put("message", "Yes");
        }});
    }
    @GetMapping("/location")
    public ResponseEntity<?> locationfetch(@RequestParam Integer phase,@RequestParam Integer floor){
        List<MeetingRoom> meetingRooms=adminService.roomFetch(phase,floor);
       // httpSession.setAttribute("meetingrooms",meetingRooms);
        if(meetingRooms.isEmpty()) {
            return ResponseEntity.ok().body(new HashMap<String, String>() {{
                put("message", "No Access");
            }});
        }
        return ResponseEntity.ok().body(meetingRooms);
    }

    @PostMapping("/availability")
    public ResponseEntity<?> availRoom(
                                   @RequestParam LocalDate requestedStartDate,
                                   @RequestParam LocalTime requestedStartTime,
                                   @RequestParam LocalTime requestedEndTime,
                                   @RequestParam LocalDate requestedEndDate){
        List<MeetingRoom> meetingRoomList= (List<MeetingRoom>) httpSession.getAttribute("meetingrooms");
        List<MeetingRoom> meetingRooms=adminService.availableRoom(meetingRoomList,requestedStartDate,
            requestedStartTime,requestedEndDate,requestedEndTime);
        httpSession.setAttribute("startdate",requestedStartTime);
        httpSession.setAttribute("enddate",requestedEndDate);
        httpSession.setAttribute("fromtime",requestedStartTime);
        httpSession.setAttribute("totime",requestedEndTime);
    return ResponseEntity.ok().body(meetingRooms);
    }

    @PostMapping("/booking")
    public ResponseEntity<?> booking(@RequestParam String roomNo ){
        LocalDate startDate= (LocalDate) httpSession.getAttribute("startdate");
        LocalDate endDate= (LocalDate) httpSession.getAttribute("enddate");
        LocalTime fromTime= (LocalTime) httpSession.getAttribute("fromtime");
        LocalTime toTime= (LocalTime) httpSession.getAttribute("totime");
        String zid=(String)httpSession.getAttribute("zid");
        Booking booking=new Booking();
        booking.setToTime(toTime);
        booking.setFromTime(fromTime);
        booking.setEndDate(endDate);
        booking.setStartDate(startDate);
        booking.setStatus("Booked");
        Booking booking1=adminService.booking(zid,roomNo,booking);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/complaint")
    public ResponseEntity<?> complaintRegister(@RequestParam Long compId){
        List<Complaint> complaints= adminService.complaintResolve(compId);
        if(complaints.isEmpty()){
            return ResponseEntity.ok().body(new HashMap<String, String>() {{
                put("message", "No");
            }});
        }
        else
            return ResponseEntity.ok().body(complaints);
    }
    @GetMapping("/complaint")
    public ResponseEntity<?> complaintHistory(){
        List<Complaint> complaints=adminService.complaintHistory();
        if(complaints.isEmpty()){
            return ResponseEntity.ok().body(new HashMap<String, String>() {{
                put("message", "No");
            }});
        }
        else
            return ResponseEntity.ok().body(complaints);
    }
    @GetMapping("/bookHistory")
    public ResponseEntity<?> bookHistory(){
        String zid=(String) httpSession.getAttribute("zid");
        List<Booking> bookings=adminService.bookHistory();
        if(bookings.isEmpty()){
            return ResponseEntity.ok().body("No history");
        }
        return ResponseEntity.ok().body(bookings);
    }

}
