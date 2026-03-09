package ems.controller;

import ems.entity.LeaveRequest;
import ems.entity.User;
import ems.repository.LeaveRequestRepository;
import ems.repository.UserRepository;
import ems.service.LeaveRequestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leave")
public class LeaveRequestController {

    @Autowired
    private UserRepository userRepository;

    private final LeaveRequestRepository leaveRequestRepository;

    private final LeaveRequestService service;


    public LeaveRequestController(LeaveRequestService service, LeaveRequestRepository leaveRequestRepository) {
        this.service = service;
        this.leaveRequestRepository = leaveRequestRepository;
    }

    // Apply leave (USER)
    @PostMapping("/apply")
    public LeaveRequest apply(@RequestBody LeaveRequest leave) {
        return service.applyLeave(leave);
    }

    // My leaves (USER)
    @GetMapping("/my")
public List<LeaveRequest> myLeaves(Authentication auth) {

    String email = auth.getName();

    User user = userRepository.findByEmail(email).orElseThrow();

    return leaveRequestRepository.findByUserId(user.getId());
}

    // All leaves (ADMIN)
    @GetMapping("/all")
    public List<LeaveRequest> allLeaves() {
        return service.getAllLeaves();
    }

    // Approve / Reject (ADMIN)
    @PutMapping("/update/{id}")
    public LeaveRequest updateStatus(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        return service.updateStatus(id, status);
    }
}