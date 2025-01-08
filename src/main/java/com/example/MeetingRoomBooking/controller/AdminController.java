package com.example.MeetingRoomBooking.controller;

import com.example.MeetingRoomBooking.model.*;
import com.example.MeetingRoomBooking.service.AdminService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile() {
        String zid = (String) httpSession.getAttribute("zid");
        Admin admin = adminService.findPassword(zid);
        if (admin == null) {
            return ResponseEntity.ok().body(new HashMap<String, String>() {{
                put("message", "No profile found");
            }});
        }
        return ResponseEntity.ok().body(admin);
    }
    @PostMapping("/profile")
    public ResponseEntity<?> profileUpdate(@RequestBody Admin admin) {
        String zid = (String) httpSession.getAttribute("zid");
        admin.setZid(zid);
        Admin adm = adminService.profileUpdate(admin, zid);
        if (adm == null) {
            return ResponseEntity.ok().body(new HashMap<String, String>() {{
                put("message", "Unable to update");
            }});
        } else {
            return ResponseEntity.ok().body(new HashMap<String, String>() {{
                put("message", "Updated Successfully");
            }});
        }
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
        if(meetingRooms.isEmpty()) {
            return ResponseEntity.ok().body(new HashMap<String, String>() {{
                put("message", "No Access");
            }});
        }
        return ResponseEntity.ok().body(meetingRooms);
    }

    @PostMapping("/availability")
    public ResponseEntity<?> availRoom(@RequestBody List<MeetingRoom> meetingRoom,
                                   @RequestParam LocalDate requestedStartDate,
                                   @RequestParam LocalTime requestedStartTime,
                                   @RequestParam LocalTime requestedEndTime,
                                   @RequestParam LocalDate requestedEndDate){
        List<MeetingRoom> meetingRooms=adminService.availableRoom(meetingRoom,requestedStartDate,
            requestedStartTime,requestedEndDate,requestedEndTime);
        httpSession.setAttribute("startdate",requestedStartDate);
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
    @GetMapping("/currentHistory")
    public ResponseEntity<?> currentHistory(){
        List<Booking> bookings=adminService.allCurrentHistory();
        if(bookings.isEmpty())
        {
            return ResponseEntity.ok().body(new ArrayList<Booking>());
//            return ResponseEntity.ok().body(new HashMap<String, String>() {{

//                put("message", "No");

//            }});
        }
        else
            return ResponseEntity.ok().body(bookings);
    }
    @GetMapping("/adminCurrentHistory")
    public ResponseEntity<?> adminCurrentHistory(){
        String zid=(String) httpSession.getAttribute("zid");
        List<Booking> bookings=adminService.adminCurrentHistory(zid);
        if(bookings.isEmpty())
        {

            return ResponseEntity.ok().body(new ArrayList<Booking>());

//            return ResponseEntity.ok().body(new HashMap<String, String>() {{

//                put("message", "No");

//            }});

        }

        else

            return ResponseEntity.ok().body(bookings);

    }

    @GetMapping("/bookHistory")

    public ResponseEntity<?> bookHistory(){

        List<Booking> bookings=adminService.allBookHistory();
        if(bookings==null){
            return ResponseEntity.ok().body(new ArrayList<Booking>());

//            return ResponseEntity.ok().body(new HashMap<String, String>() {{

//                put("message", "No");

//            }});

        }

        return ResponseEntity.ok().body(bookings);

    }

    @GetMapping("/adminBookHistory")

    public ResponseEntity<?> adminBookHistory(){

        String zid=(String) httpSession.getAttribute("zid");

        List<Booking> bookings=adminService.adminBookHistory(zid);

        if(bookings==null){

            return ResponseEntity.ok().body(new ArrayList<Booking>());

        }

        return ResponseEntity.ok().body(bookings);

    }

    @GetMapping("/cancelRoom/{bookId}")

    public  ResponseEntity<?> cancelRoom(@PathVariable Long bookId){

        System.out.println(bookId);

        Booking booking1=adminService.cancelRoom(bookId);

        if(booking1==null){

            return ResponseEntity.ok().body(new HashMap<String, String>() {{

                put("message", "Unable to cancel");

            }});

        }

        System.out.println("haiiiii");

        return ResponseEntity.ok().body(new HashMap<String, String>() {{

            put("message", "Cancelled successfully");

        }});

    }


}
