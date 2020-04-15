package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private List<Company> companies;

    @GetMapping
    public List<Company> getCompanies() {
        return companies;
    }

    @GetMapping("/{companyID}")
    public Company getCompanyByID(@PathVariable int companyID) {
        return companies.stream()
                .filter(company -> company.getCompanyID() == companyID)
                .findAny()
                .orElse(null);
    }

    @GetMapping("/{companyID}/employees")
    public List<Employee> getCompanyEmployees(@PathVariable int companyID) {
        Optional<Company> targetCompany = companies.stream()
                .filter(company -> company.getCompanyID() == companyID)
                .findAny();

        return targetCompany.map(Company::getEmployees).orElse(null);
    }
}
