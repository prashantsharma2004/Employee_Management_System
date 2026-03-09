package ems.repository;

import ems.entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    // User ki sari leave
    List<LeaveRequest> findByUserId(Long userId);
}