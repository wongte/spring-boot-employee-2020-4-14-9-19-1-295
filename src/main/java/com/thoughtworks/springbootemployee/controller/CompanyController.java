package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.CompanyInformationManager;
import com.thoughtworks.springbootemployee.ListUtility;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    CompanyService companyService;

    CompanyInformationManager companyInformationManager;

    public CompanyController() {
        companyInformationManager = CompanyInformationManager.getInstance();
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Company> getCompaniesWithPaging(@RequestParam Integer page, @RequestParam Integer pageSize) {
        return companyService.getCompaniesWithPaging(page, pageSize);
    }

    @GetMapping
    public List<Company> getCompanies() {
        return companyService.getCompanies();
    }

    @GetMapping("/{companyID}")
    public Company getCompanyByID(@PathVariable int companyID) {
        return companyService.getCompanyByID(companyID);
    }

    @GetMapping("/{companyID}/employees")
    public List<Employee> getCompanyEmployees(@PathVariable int companyID) {
        return companyService.getCompanyEmployees(companyID);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company create(@RequestBody Company newCompany) {
        return companyService.create(newCompany);
    }

    @DeleteMapping("/{targetCompanyID}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void removeAllEmployees(@PathVariable int targetCompanyID) {
        companyService.removeAllEmployees(targetCompanyID);
    }

    @PutMapping("/{targetCompanyID}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Company update(@PathVariable Integer targetCompanyID, @RequestBody Company updatedCompany) {
        return companyService.update(targetCompanyID, updatedCompany);
    }
}
