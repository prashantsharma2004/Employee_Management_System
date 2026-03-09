package ems.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ems.entity.Employee;
import ems.repository.EmployeeRepository;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repo;

    // Add
    public Employee addEmployee(Employee emp) {
        return repo.save(emp);
    }

    // Get All
    public List<Employee> getAllEmployees() {
        return repo.findAll();
    }

    // Get By Id
    public Employee getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee Not Found"));
    }

    // Update
    public Employee updateEmployee(Long id, Employee emp) {

        Employee old = getById(id);

        old.setName(emp.getName());
        old.setEmail(emp.getEmail());
        old.setDepartment(emp.getDepartment());
        old.setSalary(emp.getSalary());

        return repo.save(old);
    }

    // Delete
    public void deleteEmployee(Long id) {
        repo.deleteById(id);
    }
}