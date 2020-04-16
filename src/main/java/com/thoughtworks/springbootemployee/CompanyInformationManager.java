package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;

import java.util.*;
import java.util.stream.Collectors;

public class CompanyInformationManager {
    private static CompanyInformationManager instance;
    private List<Company> companies = new ArrayList<>();

    public static CompanyInformationManager getInstance() {
        if (instance == null) {
            instance = new CompanyInformationManager();
        }
        return instance;
    }

    public void reset() {
        companies = new ArrayList<>();
    }

    public void addCompany(Company company) {
        companies.add(company);
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public List<Employee> getEmployees() {
        return companies.stream().map(Company::getEmployees).flatMap(Collection::stream).collect(Collectors.toList());
    }

    public void addEmployee(Employee newEmployee) {
        Company targetCompany = findCompanyByID(newEmployee.getCompanyID());
        targetCompany.getEmployees().add(newEmployee);
    }

    public List<Employee> findAllEmployeeInCompany(int targetCompanyID) {
        return findCompanyByID(targetCompanyID).getEmployees();
    }

    public void deleteEmployee(int employeeID) {
        Employee firedEmployee = findEmployeeByID(employeeID);
        Company company = findCompanyByID(firedEmployee.getCompanyID());
        company.getEmployees().removeIf(employee -> employee.getId() == employeeID);
    }

    public Company findCompanyByID(int companyID) {
        return companies.stream()
                .filter(company -> company.getCompanyID() == companyID)
                .findAny()
                .orElse(null);
    }

    public Employee findEmployeeByID(int employeeID) {
        return getEmployees().stream()
                .filter(employee -> employee.getId() == employeeID)
                .findAny()
                .orElse(null);
    }

    public void updateEmployee(Employee updatedEmployee) {
        Optional<Employee> targetEmployeeOptional = getEmployees().stream().filter(employee -> employee.getId() == updatedEmployee.getId()).findAny();
        if (!targetEmployeeOptional.isPresent()) return;
        Employee employee = targetEmployeeOptional.get();
        employee.setAge(updatedEmployee.getAge());
        employee.setGender(updatedEmployee.getGender());
        employee.setName(updatedEmployee.getName());
        employee.setSalary(updatedEmployee.getSalary());
    }
}
