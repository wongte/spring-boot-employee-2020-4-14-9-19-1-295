package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.CompanyInformationManager;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    CompanyInformationManager companyInformationManager;

    @Autowired
    EmployeeService employeeService;

    public EmployeeController() {
        companyInformationManager = CompanyInformationManager.getInstance();
    }

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(params = {"gender"})
    public List<Employee> getEmployees(@RequestParam String gender) {
        return employeeService.getEmployeesByGender(gender);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Employee> getEmployeesWithPaging(@RequestParam Integer page, @RequestParam Integer pageSize) {
        return employeeService.getEmployeesWithPaging(page, pageSize);
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{employeeID}")
    public Employee getEmployeeById(@PathVariable int employeeID) {
        return employeeService.findEmployeeByID(employeeID);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@RequestBody Employee newEmployee) {
        return employeeService.create(newEmployee);
    }

    @DeleteMapping("/{targetEmployeeID}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable Integer targetEmployeeID) {
        employeeService.delete(targetEmployeeID);
    }

    @PutMapping("/{targetEmployeeID}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Employee update(@PathVariable Integer targetEmployeeID, @RequestBody Employee updatedEmployee) {
        return employeeService.update(targetEmployeeID, updatedEmployee);
    }
}
