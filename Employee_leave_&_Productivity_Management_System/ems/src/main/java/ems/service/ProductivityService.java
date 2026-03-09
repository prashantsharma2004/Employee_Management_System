package ems.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ems.entity.ProductivityLog;
import ems.entity.User;
import ems.repository.ProductivityRepository;
import ems.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProductivityService {

    @Autowired
    private ProductivityRepository repo;

    @Autowired
    private UserRepository userRepo;

    // Add Work Log
    public ProductivityLog addLog(ProductivityLog log) {

        //JWY Logged in user ka email nikalna hai
        String email= SecurityContextHolder.getContext()
        .getAuthentication()
        .getName();

        // User find karna hai email se
        User user= userRepo.findByEmail(email)
        .orElseThrow();
        
        //user set karna hai log me
        log.setUser(user);

        return repo.save(log);
    }

    // User Logs
    public List<ProductivityLog> getUserLogs(Long userId) {
        return repo.findByUser_Id(userId);
    }

    // Admin - All Logs
    public List<ProductivityLog> getAll() {
        return repo.findAll();

    }

     // User Logs by Date
    public List<ProductivityLog> getUserLogsByDate(Long userId, LocalDate date) {
    return repo.findByUser_IdAndWorkDate(userId, date);
}
    
// Admin - Employee Work Summary 
    public List<Object[]> getEmployeeWorkSummary() {
        return repo.getEmployeeWorkSummary();
    }


}