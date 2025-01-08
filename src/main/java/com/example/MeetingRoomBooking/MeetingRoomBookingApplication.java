package com.example.MeetingRoomBooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MeetingRoomBookingApplication {
	public static void main(String[] args) {

		SpringApplication.run(MeetingRoomBookingApplication.class, args);
		System.out.println("Hello");
	}

}
