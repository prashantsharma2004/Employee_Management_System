package ems.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ems.entity.User;
import ems.entity.WorkLog;

public interface WorkLogRepository extends JpaRepository<WorkLog, Long> {

    List<WorkLog> findByUser(User user);
}