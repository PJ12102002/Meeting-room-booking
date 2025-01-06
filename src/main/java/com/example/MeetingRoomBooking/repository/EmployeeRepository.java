package com.example.MeetingRoomBooking.repository;

import com.example.MeetingRoomBooking.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,String> {

}
