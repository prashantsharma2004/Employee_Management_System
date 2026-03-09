package ems.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ems.entity.ProductivityLog;

import java.time.LocalDate;
import java.util.List;

public interface ProductivityRepository
        extends JpaRepository<ProductivityLog, Long> {

    List<ProductivityLog> findByUser_Id(Long userId);

    List<ProductivityLog> findByUser_IdAndWorkDate(Long userId, LocalDate date);

 //admin  har employee ke total hours dekh sake
    @Query("""
SELECT p.user.email, SUM(p.hours)
FROM ProductivityLog p
GROUP BY p.user.email
""")
List<Object[]> getEmployeeWorkSummary();

    
}