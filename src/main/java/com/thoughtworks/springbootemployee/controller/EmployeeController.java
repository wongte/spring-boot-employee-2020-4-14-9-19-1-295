package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.CompanyInformationManager;
import com.thoughtworks.springbootemployee.ListUtility;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    CompanyInformationManager companyInformationManager;

    public EmployeeController() {
        companyInformationManager = CompanyInformationManager.getInstance();
    }

    @GetMapping
    public List<Employee> getEmployees(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String gender
    ) {
        List<Employee> employees = companyInformationManager.getEmployees();
        return new ListUtility<Employee>().getListByPage(employees, page, pageSize);
    }

    @GetMapping("/{employeeID}")
    public Employee getEmployeeById(@PathVariable int employeeID) {
        return companyInformationManager.findEmployeeByID(employeeID);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@RequestBody Employee newEmployee) {
        companyInformationManager.addEmployee(newEmployee);
        return newEmployee;
    }

    @DeleteMapping("/{targetEmployeeID}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable Integer targetEmployeeID) {
        companyInformationManager.deleteEmployee(targetEmployeeID);
    }

    @PutMapping("/{targetEmployeeID}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void update(@PathVariable Integer targetEmployeeID, @RequestBody Employee updatedEmployee) {
        updatedEmployee.setId(targetEmployeeID);
        companyInformationManager.updateEmployee(updatedEmployee);
    }
}
