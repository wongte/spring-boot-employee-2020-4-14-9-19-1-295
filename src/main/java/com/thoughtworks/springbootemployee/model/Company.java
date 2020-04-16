package com.thoughtworks.springbootemployee.model;

import java.util.ArrayList;
import java.util.List;

public class Company {
    private int companyID;
    private String companyName;
    private List<Employee> employees = new ArrayList<>();

    public Company() {
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

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
