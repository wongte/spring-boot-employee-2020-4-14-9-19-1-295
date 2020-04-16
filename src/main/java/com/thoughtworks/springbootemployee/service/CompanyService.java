package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    public List<Company> getCompaniesWithPaging(Integer page, Integer pageSize) {
        return companyRepository.findAll(page, pageSize);
    }

    public List<Company> getCompanies() {
        return companyRepository.findAll();
    }

    public Company getCompanyByID(int companyID) {
        return companyRepository.findByID(companyID);
    }

    public List<Employee> getCompanyEmployees(int companyID) {
        Company company = companyRepository.findByID(companyID);
        return companyRepository.findEmployees(companyID);
    }

    public Company create(Company newCompany) {
        return companyRepository.add(newCompany);
    }

    public void removeAllEmployees(int targetCompanyID) {
        Company company = companyRepository.findByID(targetCompanyID);
        company.setEmployees(new ArrayList<>());
        companyRepository.update(targetCompanyID, company);
    }

    public Company update(Integer targetCompanyID, Company updatedCompany) {
        Company company = companyRepository.findByID(targetCompanyID);
        company.setCompanyName(updatedCompany.getCompanyName());

        return companyRepository.update(targetCompanyID, company);
    }
}
