package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.CompanyInformationManager;
import com.thoughtworks.springbootemployee.ListUtility;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    CompanyInformationManager companyInformationManager;

    public CompanyController() {
        companyInformationManager = CompanyInformationManager.getInstance();
    }

    @GetMapping
    public List<Company> getCompanies(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize
    ) {
        List<Company> companies = companyInformationManager.getCompanies();
        return new ListUtility<Company>().getListByPage(companies, page, pageSize);
    }

    @GetMapping("/{companyID}")
    public Company getCompanyByID(@PathVariable int companyID) {
        return companyInformationManager.findCompanyByID(companyID);
    }

    @GetMapping("/{companyID}/employees")
    public List<Employee> getCompanyEmployees(@PathVariable int companyID) {
        return companyInformationManager.findAllEmployeeInCompany(companyID);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company create(@RequestBody Company newCompany) {
        companyInformationManager.addCompany(newCompany);
        return newCompany;
    }

    @DeleteMapping("/{targetCompanyID}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void removeAllEmployee(@PathVariable int targetCompanyID) {
        companyInformationManager.removeAllEmployeeInCompany(targetCompanyID);
    }

    @PutMapping("/{targetCompanyID}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void update(@PathVariable Integer targetCompanyID, @RequestBody Company updatedCompany) {
        updatedCompany.setCompanyID(targetCompanyID);
        companyInformationManager.updateCompany(updatedCompany);
    }
}
