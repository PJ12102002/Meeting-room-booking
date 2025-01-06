package com.example.MeetingRoomBooking.controller;

import com.example.MeetingRoomBooking.model.Booking;
import com.example.MeetingRoomBooking.model.Complaint;
import com.example.MeetingRoomBooking.model.Employee;
import com.example.MeetingRoomBooking.model.MeetingRoom;
import com.example.MeetingRoomBooking.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;
    HttpSession httpSession;
    @PostMapping("/login")
    public ResponseEntity<?> loginCredit(HttpSession httpSession, @RequestParam String zid, @RequestParam String password) {
        this.httpSession = httpSession;
        httpSession.setAttribute("zid", zid);

        // Fetch the employee based on the provided zid (username)
        Employee employee = userService.employeeDetails(zid);

        // If no employee is found, return an error message
        if (employee == null) {
            return ResponseEntity.ok().body("Username or password is incorrect.");
        }

        // Now, verify the password with the stored password
        if (!employee.getPassword().equals(password)) {
            // If passwords don't match, return an error message
            return ResponseEntity.ok().body("Username or password is incorrect.");
        }

        // If both zid and password match, return employee details
        return ResponseEntity.ok().body(employee);
    }
    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestParam String username,@RequestParam String password){
    Employee e=userService.signUser(username,password);
    if(e==null)
        return ResponseEntity.ok().body("null");
    else
        return ResponseEntity.ok().build();
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile() {
        String zid = (String) httpSession.getAttribute("zid");
        Employee employee = userService.getEmployeeProfile(zid);
        if (employee == null) {
            return ResponseEntity.ok().body(new HashMap<String, String>() {{
                put("message", "No profile found");
            }});
        }
        return ResponseEntity.ok().body(employee);
    }

    @PostMapping("/profile")
    public ResponseEntity<?> profileUpdate(@RequestBody  Employee employee){
        String zid= (String) httpSession.getAttribute("zid");
        employee.setZid(zid);
        Employee emp=userService.profile(employee,zid);
        if(emp==null){
            return ResponseEntity.ok().body(new HashMap<String, String>() {{
                put("message", "unable to update");
            }});
        }
        else
            return ResponseEntity.ok().body(new HashMap<String, String>() {{
                put("message", "Updated Succesfully");
            }});
    }

    @GetMapping("/location")
    public ResponseEntity<?> locationfetch(@RequestParam Integer phase,@RequestParam Integer floor){
        String zid=(String)httpSession.getAttribute("zid");
        List<MeetingRoom> meetingRooms=userService.roomFetch(zid,phase,floor);
        httpSession.setAttribute("meetingrooms",meetingRooms);
        if(meetingRooms.isEmpty())
        {
            return ResponseEntity.ok().body(new HashMap<String, String>() {{
                put("message", "No Access");
            }});
        }
        return ResponseEntity.ok().body(meetingRooms);
    }

    @PostMapping("/availability")
    public ResponseEntity<?> availRoom(@RequestParam LocalDate requestedStartDate,
                                       @RequestParam LocalTime requestedStartTime,
                                       @RequestParam LocalTime requestedEndTime,
                                       @RequestParam LocalDate requestedEndDate){
    String zid=(String)httpSession.getAttribute("zid");
    List<MeetingRoom> meetingRoomList= (List<MeetingRoom>) httpSession.getAttribute("meetingrooms");
    List<MeetingRoom> meetingRooms=userService.availableRoom(meetingRoomList,requestedStartDate,
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
        Booking booking1=userService.booking(zid,roomNo,booking);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/complaint")
    public ResponseEntity<?> complaintRegister(@RequestBody Complaint complaint){
        String zid=(String)httpSession.getAttribute("zid");
        complaint.setZid(zid);
        complaint.setStatus(0);
        userService.complaintRegister(complaint);
        return ResponseEntity.ok().body(new HashMap<String, String>() {{
            put("message", "Register");
        }});
    }
    @GetMapping("/bookHistory")
    public ResponseEntity<?> bookHistory(){
        String zid=(String) httpSession.getAttribute("zid");
        List<Booking> bookings=userService.bookHistory(zid);
        if(bookings.isEmpty()){
            return ResponseEntity.ok().body("No history");
        }
        return ResponseEntity.ok().body(bookings);
    }
    @GetMapping("/currentHistory")
    public ResponseEntity<?> currentHistory(){
    String zid=(String) httpSession.getAttribute("zid");
    List<Booking> bookings=userService.currentHistory(zid);
    if(bookings.isEmpty()){
        return ResponseEntity.ok().body("No Current history");
    }
    return ResponseEntity.ok().body(bookings);
    }

}


















//@PostMapping("/loginAdd")
//public  ResponseEntity<?> loginAddAndCheck(@RequestParam String zid, @RequestParam String pass){
//    Login log= userService.loginCheck(zid,pass);
//    if(log==null)
//        return ResponseEntity.ok().body("No user found");
//    else
//    return ResponseEntity.ok().body(log);
//}
//@PostMapping("/employee")
//    public ResponseEntity<?> fetchEmployee(HttpSession session,@RequestParam String zid){
//    session.setAttribute("zid",zid);
//    Optional<Employee> emp=userService.employeeDetails(zid);
//    return ResponseEntity.ok().body(emp.get());
//}

//@PostMapping("/location")
//    public ResponseEntity<?> fetchMeetRoom(HttpSession session,@RequestParam Integer phaseNo,@RequestParam Integer floorNo
//                                           ){
//    List<MeetingRoom> meetingList=userService.meetingRoomList(session,phaseNo,floorNo);
//    session.setAttribute("meetingList",meetingList);
//    return ResponseEntity.ok().body(meetingList);
//}
//@PostMapping("/fetchroom")
//    public ResponseEntity<?> fetchMeetRoom(HttpSession session, String date, String from, String to){
//    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
//    LocalDate d= LocalDate.parse(date,dateFormatter);
//    LocalTime f= LocalTime.parse(from,timeFormatter) ;
//    LocalTime t= LocalTime.parse(to,timeFormatter) ;
//    List<MeetingRoom> mr=userService.bookMeeting(session, d,f,t);
//    System.out.println(mr);
//    session.setAttribute("date",d);
//    session.setAttribute("from",f);
//    session.setAttribute("to",t);
//    return ResponseEntity.ok().body(mr);
//}
//@PostMapping("/booking")
//    public  ResponseEntity<?> bookRoom(HttpSession session,@RequestBody Booking booking,@RequestParam Long roomId){
//    Booking book=userService.booking(booking,session,roomId);
//    return  ResponseEntity.ok().body(book);
//}


