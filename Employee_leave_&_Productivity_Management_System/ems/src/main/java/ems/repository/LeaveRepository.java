package ems.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ems.entity.LeaveRequest;

public interface LeaveRepository extends JpaRepository<LeaveRequest, Long> {
List<LeaveRequest> findByUserId(Long userId);

}
