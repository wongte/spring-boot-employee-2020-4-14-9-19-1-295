package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private List<Company> companies;

    public CompanyController() {
        companies = new ArrayList<>();
    }

    @GetMapping
    public List<Company> getCompanies(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize
    ) {
        if (page == null) page = 1;
        if (pageSize == null) pageSize = companies.size();

        int startIndex = (page - 1) * pageSize;
        int endIndex = page * pageSize - 1;

        if (endIndex >= companies.size()) {
            return null;
        }

        return IntStream.range(startIndex, endIndex + 1).boxed()
                .map(index -> companies.get(index))
                .collect(Collectors.toList());
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company create(@RequestBody Company newCompany) {
        companies.add(newCompany);
        return newCompany;
    }

    @DeleteMapping("/{targetCompanyID}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable int targetCompanyID) {
        companies.removeIf(company -> company.getCompanyID() == targetCompanyID);
    }

    @PutMapping("/{targetCompanyID}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void update(@PathVariable Integer targetCompanyID, @RequestBody Company updatedCompany) {
        for (int index = 0; index < companies.size(); index++) {
            if (companies.get(index).getCompanyID() == targetCompanyID) {
                companies.set(index, updatedCompany);
            }
        }
    }
}
