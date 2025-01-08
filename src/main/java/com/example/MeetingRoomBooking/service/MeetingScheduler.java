package com.example.MeetingRoomBooking.service;




import com.example.MeetingRoomBooking.dto.EmailDetails;
import com.example.MeetingRoomBooking.model.Admin;
import com.example.MeetingRoomBooking.model.Booking;
import com.example.MeetingRoomBooking.model.Employee;
import com.example.MeetingRoomBooking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;



@Component



public class MeetingScheduler {



    @Autowired
    private EmailService emailService;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private UserService employeeService;
    @Autowired
    private AdminService adminService;
    // Scheduled task runs every minute
    @Scheduled(cron = "0 * * * * *")  // This will check every minute
    public void checkMeetingsAndSendReminder() {
        System.out.println("hello");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate l=LocalDate.now();
        String localDate=l.format(dateFormatter);
        LocalDate parsedDate = LocalDate.parse(localDate, dateFormatter);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime t= LocalTime.now();
        String localtime=t.format(timeFormatter);
        LocalTime parsedTime=LocalTime.parse(localtime,timeFormatter);
        List<Booking> upcomingMeetings = bookingRepository.findBookingsWhereTodayAndBooked(parsedTime.plusHours(1),parsedDate);
        for (Booking meeting : upcomingMeetings) {
            EmailDetails details = new EmailDetails();
            Employee employee=employeeService.getEmployeeProfile(meeting.getZid());
            Admin admin=adminService.findPassword(meeting.getZid());
            if(employee!=null) {
                details.setTo(employee.getEmail());  // Get the recipient's email
                details.setSubject("Meeting Reminder");
                details.setBody("This is a reminder that your meeting " +
                        "is scheduled to start at " + meeting.getFromTime());
                String status = emailService.sendSimpleMail(details);
                System.out.println(status);

            }
            else{
                details.setTo(admin.getEmail());  // Get the recipient's email
                details.setSubject("Meeting Reminder");
                details.setBody("This is a reminder that your meeting '" +
                        "' is scheduled to start at " + meeting.getFromTime());
                String status = emailService.sendSimpleMail(details);
                System.out.println(status);
            }
        }
    }



}
