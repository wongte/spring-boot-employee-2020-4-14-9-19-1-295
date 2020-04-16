package com.thoughtworks.springbootemployee.model;

import java.util.ArrayList;
import java.util.List;

public class CompanyResponse {
    private int companyID;
    private String companyName;
    private int employeesNumber;
    private List<Employee> employees = new ArrayList<>();

    public CompanyResponse(Company company) {
        this.companyID = company.getCompanyID();
        this.companyName = company.getCompanyName();
        this.employees = company.getEmployees();
        this.employeesNumber = this.employees.size();
    }

    public CompanyResponse() {
    }

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getEmployeesNumber() {
        return employeesNumber;
    }

    public void setEmployeesNumber(int employeesNumber) {
        this.employeesNumber = employeesNumber;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
