package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CompanyInfoManager {
    private static CompanyInfoManager instance;
    private List<Company> companies = new ArrayList<>();
    private List<Employee> employees = new ArrayList<>();
    Map<Integer, Integer> employeeCompanyMap = new HashMap<>();

    public static CompanyInfoManager getInstance() {
        if (instance == null) {
            instance = new CompanyInfoManager();
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
    }
    public void deleteEmployee(Employee employee) {
        employeeCompanyMap.remove(employee.getId());
        employees.remove(employee);
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
        employeeCompanyMap.remove(employeeID);
        employees.removeIf(employee -> employee.getId() == employeeID);
    }
}
