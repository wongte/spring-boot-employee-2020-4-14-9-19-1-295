package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public List<Employee> getEmployeesByGender(String gender) {
        return employeeRepository.findAllByGender(gender);
    }

    public List<Employee> getEmployeesWithPaging(Integer page, Integer pageSize) {
        return employeeRepository.findAll(PageRequest.of(page, pageSize)).getContent();
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee findEmployeeByID(Integer employeeID) {
        return employeeRepository.findById(employeeID).orElse(null);
    }

    public Employee create(Employee newEmployee) {
        return employeeRepository.save(newEmployee);
    }

    public void delete(Integer targetEmployeeID) {
        employeeRepository.deleteById(targetEmployeeID);
    }

    public Employee update(Integer targetEmployeeID, Employee updatedEmployee) {
        updatedEmployee.setId(targetEmployeeID);
        Employee employee = employeeRepository.findById(targetEmployeeID).orElse(null);

        if (employee == null) {
            return null;
        }
        if (updatedEmployee.getName() != null) {
            employee.setName(updatedEmployee.getName());
        }
        if (updatedEmployee.getGender() != null) {
            employee.setGender(updatedEmployee.getGender());
        }
        if (updatedEmployee.getAge() != null) {
            employee.setAge(updatedEmployee.getAge());
        }
        if (updatedEmployee.getSalary() != null) {
            employee.setSalary(updatedEmployee.getSalary());
        }
        return employeeRepository.save(employee);
    }
}
