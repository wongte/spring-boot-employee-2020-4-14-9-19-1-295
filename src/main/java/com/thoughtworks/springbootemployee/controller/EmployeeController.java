package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.CompanyInfoManager;
import com.thoughtworks.springbootemployee.ListUtility;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    CompanyInfoManager companyInfoManager;

    public EmployeeController() {
        companyInfoManager = CompanyInfoManager.getInstance();
    }

    @GetMapping
    public List<Employee> getEmployees(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String gender
    ) {
        List<Employee> employees = companyInfoManager.getEmployees();
        return new ListUtility<Employee>().getListByPage(employees, page, pageSize);
    }

    @GetMapping("/{employeeID}")
    public Employee getEmployeeById(@PathVariable int employeeID) {
        return companyInfoManager.findEmployeeByID(employeeID);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@RequestBody Employee newEmployee) {
        companyInfoManager.addEmployee(newEmployee);
        return newEmployee;
    }

    @DeleteMapping("/{targetEmployeeID}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable Integer targetEmployeeID) {
        companyInfoManager.deleteEmployee(targetEmployeeID);
    }

    @PutMapping("/{targetEmployeeID}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void update(@PathVariable Integer targetEmployeeID, @RequestBody Employee updatedEmployee) {
        updatedEmployee.setId(targetEmployeeID);
        companyInfoManager.updateEmployee(updatedEmployee);
    }
}
