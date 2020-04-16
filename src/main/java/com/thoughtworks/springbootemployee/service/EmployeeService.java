package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public List<Employee> getEmployeesByGender(String gender) {
        return employeeRepository.findByGender(gender);
    }

    public List<Employee> getEmployeesWithPaging(Integer page, Integer pageSize) {
        return employeeRepository.findAll(page, pageSize);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee findEmployeeByID(int employeeID) {
        return employeeRepository.findByID(employeeID);
    }

    public Employee create(Employee newEmployee) {
        return employeeRepository.add(newEmployee);
    }

    public void delete(Integer targetEmployeeID) {
        employeeRepository.delete(targetEmployeeID);
    }

    public Employee update(Integer targetEmployeeID, Employee updatedEmployee) {
        return employeeRepository.update(targetEmployeeID, updatedEmployee);
    }
}
