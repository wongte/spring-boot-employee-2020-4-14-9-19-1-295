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

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }

    public List<Employee> getEmployees() {
        return companies.stream().map(Company::getEmployees).flatMap(Collection::stream).collect(Collectors.toList());
    }

    public Company findCompanyByID(int companyID) {
        return companies.stream()
                .filter(company -> company.getCompanyID() == companyID)
                .findAny()
                .orElse(null);
    }
}
