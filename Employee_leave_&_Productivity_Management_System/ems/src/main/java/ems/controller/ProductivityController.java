package ems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ems.entity.ProductivityLog;
import ems.service.ProductivityService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/productivity")
@CrossOrigin("*")
public class ProductivityController {

    @Autowired
    private ProductivityService service;

    // Add Work
    @PostMapping("/add")
    public ProductivityLog add(@RequestBody ProductivityLog log) {
        return service.addLog(log);
    }

    // My Logs
    @GetMapping("/user/{id}")
    public List<ProductivityLog> userLogs(@PathVariable Long id) {
        return service.getUserLogs(id);
    }

    // Admin - All
    @GetMapping("/all")
    public List<ProductivityLog> all() {
        return service.getAll();
    }

    //fillter by date
    @GetMapping("/user/{id}/date")
public List<ProductivityLog> logsByDate(
        @PathVariable Long id,
        @RequestParam String date
) {

    return service.getUserLogsByDate(id, LocalDate.parse(date));
}

// Admin - Employee Work Summary
@GetMapping("/admin/summary")
public List<Object[]> WorkSummary() {
    return service.getEmployeeWorkSummary();
}

}