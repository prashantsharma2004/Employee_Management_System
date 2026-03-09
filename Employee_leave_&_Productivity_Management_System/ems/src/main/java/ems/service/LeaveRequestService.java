package ems.service;

import ems.entity.LeaveRequest;
import ems.entity.User;
import ems.repository.LeaveRequestRepository;
import ems.repository.UserRepository;

//import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

//import java.security.Security;
import java.util.List;

@Service
public class LeaveRequestService {

    private final UserRepository userRepo;
    private final LeaveRequestRepository repo;

    public LeaveRequestService(LeaveRequestRepository repo,
                                UserRepository userRepo)
     {
        this.repo = repo;
        this.userRepo = userRepo;
    }

    // Apply Leave
    public LeaveRequest applyLeave(LeaveRequest leave) {

        //JWT se email nikalna hai
        String email=SecurityContextHolder
        .getContext()
        .getAuthentication()
        .getName();

        //user find karo email se
        User user= userRepo.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("User not found"));   
        
        //correct userId set karo
        leave.setUserId(user.getId());

        leave.setStatus("PENDING");
        return repo.save(leave);
    }

    // User ki leave
    public List<LeaveRequest> getUserLeaves(Long userId) {
        return repo.findByUserId(userId);
    }

    // All leaves (Admin)
    public List<LeaveRequest> getAllLeaves() {
        return repo.findAll();
    }

    // Approve / Reject
    public LeaveRequest updateStatus(Long id, String status) {
        LeaveRequest leave = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave not found"));

        leave.setStatus(status);
        return repo.save(leave);
    }
}