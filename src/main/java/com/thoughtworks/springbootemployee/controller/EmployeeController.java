package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.CompanyInfoManager;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        if (page == null) page = 1;
        if (pageSize == null) pageSize = employees.size();

        int startIndex = (page - 1) * pageSize;
        int endIndex = page * pageSize - 1;

        if (endIndex >= employees.size()) {
            endIndex = employees.size() - 1;
        }

        List<Employee> pagedEmployees = IntStream.range(startIndex, endIndex + 1).boxed()
                .map(employees::get)
                .collect(Collectors.toList());

        if (gender != null) {
            pagedEmployees = pagedEmployees.stream()
                    .filter(employee -> employee.getGender().equals(gender))
                    .collect(Collectors.toList());
        }
        return pagedEmployees;
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
        List<Employee> employees = companyInfoManager.getEmployees();
        int index = employees.indexOf(updatedEmployee);
        employees.set(index, updatedEmployee);
    }
}
