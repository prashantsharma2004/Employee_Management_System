package ems.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ems.entity.User;
import ems.entity.WorkLog;
import ems.repository.WorkLogRepository;

@Service
public class WorkLogService {

    @Autowired
    private WorkLogRepository repo;

    public WorkLog saveWork(User user, String task, int hours) {

        WorkLog log = new WorkLog();

        log.setUser(user);
        log.setTask(task);
        log.setHours(hours);
        log.setDate(LocalDate.now());

        return repo.save(log);
    }

    public List<WorkLog> getMyWork(User user) {
        return repo.findByUser(user);
    }
}