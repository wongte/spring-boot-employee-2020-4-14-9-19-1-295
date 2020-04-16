package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.CompanyInformationManager;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.CompanyResponse;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    CompanyService companyService;

    CompanyInformationManager companyInformationManager;

    public CompanyController() {
        companyInformationManager = CompanyInformationManager.getInstance();
    }

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<CompanyResponse> getCompaniesWithPaging(@RequestParam Integer page, @RequestParam Integer pageSize) {
        return companyService.getCompaniesWithPaging(page, pageSize).stream().map(CompanyResponse::new).collect(Collectors.toList());
    }

    @GetMapping
    public List<CompanyResponse> getCompanies() {
        return companyService.getCompanies().stream().map(CompanyResponse::new).collect(Collectors.toList());
    }

    @GetMapping("/{companyID}")
    public CompanyResponse getCompanyByID(@PathVariable int companyID) {
        return new CompanyResponse(companyService.getCompanyByID(companyID));
    }

    @GetMapping("/{companyID}/employees")
    public List<Employee> getCompanyEmployees(@PathVariable int companyID) {
        return companyService.getCompanyEmployees(companyID);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyResponse create(@RequestBody Company newCompany) {
        return new CompanyResponse(companyService.create(newCompany));
    }

    @DeleteMapping("/{targetCompanyID}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void removeAllEmployees(@PathVariable int targetCompanyID) {
        companyService.removeAllEmployees(targetCompanyID);
    }

    @PutMapping("/{targetCompanyID}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CompanyResponse update(@PathVariable Integer targetCompanyID, @RequestBody Company updatedCompany) {
        return new CompanyResponse(companyService.update(targetCompanyID, updatedCompany));
    }
}
