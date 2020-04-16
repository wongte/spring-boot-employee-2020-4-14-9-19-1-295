package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class companyInformationManager {
    private static companyInformationManager instance;
    private List<Company> companies = new ArrayList<>();
    private List<Employee> employees = new ArrayList<>();
    Map<Integer, Integer> employeeCompanyMap = new HashMap<>();

    public static companyInformationManager getInstance() {
        if (instance == null) {
            instance = new companyInformationManager();
        }
        return instance;
    }

    public void addCompany(Company company) {
        companies.add(company);
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void addEmployee(Employee newEmployee) {
        employeeCompanyMap.put(newEmployee.getId(), newEmployee.getCompanyID());
        employees.add(newEmployee);
        Company company = findCompanyByID(newEmployee.getCompanyID());
        System.out.println(company);
        System.out.println(newEmployee.getCompanyID());
        company.incrementEmployeeNumber();
    }

    public List<Employee> findAllEmployeeInCompany(int targetCompanyID) {
        ArrayList<Integer> employeeIDInCompany = new ArrayList<>();
        employeeCompanyMap.forEach((employeeID, companyID) -> {
            if (companyID == targetCompanyID) {
                employeeIDInCompany.add(employeeID);
            }
        });
        return employees.stream().filter(employee -> employeeIDInCompany.contains(employee.getId())).collect(Collectors.toList());
    }

    public void deleteEmployee(int employeeID) {
        Employee employee = findEmployeeByID(employeeID);
        employees.remove(employee);

        Company company = findCompanyByID(employee.getCompanyID());
        employeeCompanyMap.remove(employeeID);
        company.decrementEmployeeNumber();
    }

    public void removeAllEmployeeInCompany(int companyID) {
        List<Employee> allEmployeeInCompany = findAllEmployeeInCompany(companyID);
        allEmployeeInCompany.forEach(employee -> deleteEmployee(employee.getId()));
    }

    public Company findCompanyByID(int companyID) {
        return companies.stream()
                .filter(company -> company.getCompanyID() == companyID)
                .findAny()
                .orElse(null);
    }

    public Employee findEmployeeByID(int employeeID) {
        return employees.stream()
                .filter(employee -> employee.getId() == employeeID)
                .findAny()
                .orElse(null);
    }

    public void updateEmployee(Employee updatedEmployee) {
        int index = employees.indexOf(updatedEmployee);
        employees.set(index, updatedEmployee);
        employeeCompanyMap.put(updatedEmployee.getId(), updatedEmployee.getCompanyID());
    }

    public void updateCompany(Company updatedCompany) {
        for (int index = 0; index < companies.size(); index++) {
            if (companies.get(index).getCompanyID() == updatedCompany.getCompanyID()) {
                companies.set(index, updatedCompany);
            }
        }
    }
}
