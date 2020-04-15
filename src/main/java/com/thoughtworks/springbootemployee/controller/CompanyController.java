package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.CompanyInfoManager;
import com.thoughtworks.springbootemployee.ListUtility;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    CompanyInfoManager companyInfoManager;

    public CompanyController() {
        companyInfoManager = CompanyInfoManager.getInstance();
    }

    @GetMapping
    public List<Company> getCompanies(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize
    ) {
        List<Company> companies = companyInfoManager.getCompanies();
        return new ListUtility<Company>().getListByPage(companies, page, pageSize);
    }

    @GetMapping("/{companyID}")
    public Company getCompanyByID(@PathVariable int companyID) {
        return companyInfoManager.findCompanyByID(companyID);
    }

    @GetMapping("/{companyID}/employees")
    public List<Employee> getCompanyEmployees(@PathVariable int companyID) {
        return companyInfoManager.findAllEmployeeInCompany(companyID);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company create(@RequestBody Company newCompany) {
        companyInfoManager.addCompany(newCompany);
        return newCompany;
    }

    @DeleteMapping("/{targetCompanyID}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void removeAllEmployee(@PathVariable int targetCompanyID) {
        companyInfoManager.removeAllEmployeeInCompany(targetCompanyID);
    }

    @PutMapping("/{targetCompanyID}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void update(@PathVariable Integer targetCompanyID, @RequestBody Company updatedCompany) {
        updatedCompany.setCompanyID(targetCompanyID);
        companyInfoManager.updateCompany(updatedCompany);
    }
}
