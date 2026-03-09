package ems.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ems.entity.LeaveRequest;
import ems.entity.User;
import ems.entity.WorkLog;
import ems.repository.LeaveRepository;
import ems.repository.UserRepository;
import ems.repository.WorkLogRepository;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private WorkLogRepository workRepo;
   
       //
    @GetMapping("/all-work")
    public List<WorkLog> allWork() {
        return workRepo.findAll();
    }

    // Get All Users
    @GetMapping("/all-users")
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    // Get All Leave Requests
    @GetMapping("/all-leaves")
    public List<LeaveRequest> getAllLeaves() {

        return leaveRepository.findAll();
    }
}