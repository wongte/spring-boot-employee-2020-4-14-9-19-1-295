package com.thoughtworks.springbootemployee.model;

public class Company {
    private int companyID;
    private int employeesNumber;
    private String companyName;

    public Company() {
        employeesNumber = 0;
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

    public void incrementEmployeeNumber() {
        employeesNumber++;
    }

    public void decrementEmployeeNumber() {
        employeesNumber--;
    }
}
