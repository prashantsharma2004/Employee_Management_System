package ems.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import ems.entity.LeaveRequest;
import ems.entity.User;
import ems.entity.WorkLog;
import ems.repository.LeaveRepository;
import ems.repository.UserRepository;
import ems.service.WorkLogService;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkLogService workService;

    

    // Apply Leave
    @PostMapping("/apply-leave")
    public LeaveRequest applyLeave(@RequestBody LeaveRequest leaveRequest) {

        return leaveRepository.save(leaveRequest);
    }

    // My Profile
    @GetMapping("/my-profile")
    public User myProfile(Authentication auth) {

        String email = auth.getName();

        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }


    // Add Work Log------------

@PostMapping("/add-work")
public WorkLog addWork(
        @RequestParam String task,
        @RequestParam int hours,
        Authentication auth) {

    String email = auth.getName();

    User user = userRepository.findByEmail(email).orElseThrow();

    return workService.saveWork(user, task, hours);
}

@GetMapping("/my-work")
public List<WorkLog> myWork(Authentication auth) {

    String email = auth.getName();

    User user = userRepository.findByEmail(email).orElseThrow();

    return workService.getMyWork(user);
}

}