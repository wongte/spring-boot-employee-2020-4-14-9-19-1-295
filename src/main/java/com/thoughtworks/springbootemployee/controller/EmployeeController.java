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

    @GetMapping(params = {"gender"})
    public List<Employee> getEmployees(@RequestParam String gender) {
        return companyInformationManager.getEmployeesByGender(gender);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Employee> getEmployeesWithPaging(@RequestParam Integer page, @RequestParam Integer pageSize) {
        List<Employee> employees = companyInformationManager.getEmployees();
        return new ListUtility<Employee>().getListByPage(employees, page, pageSize);
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return companyInformationManager.getEmployees();
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
