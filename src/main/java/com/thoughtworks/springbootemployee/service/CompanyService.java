package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.CompanyModelToResponse;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.CompanyResponse;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    public List<Company> getCompaniesWithPaging(Integer page, Integer pageSize) {
        return companyRepository.findAll(PageRequest.of(page, pageSize)).getContent();
    }

    public List<Company> getCompanies() {
        return companyRepository.findAll();
    }

    public Company getCompanyByID(int companyID) {
        return companyRepository.findById(companyID).orElse(null);
    }

    public List<Employee> getCompanyEmployees(int companyID) {
        Optional<Company> company = companyRepository.findById(companyID);
        return company.map(Company::getEmployees).orElse(null);
    }

    public Company create(Company newCompany) {
        return companyRepository.save(newCompany);
    }

    public void removeAllEmployees(int targetCompanyID) {
        Company company = companyRepository.findById(targetCompanyID).orElse(null);
        if (company == null) return ;
        company.setEmployees(new ArrayList<>());
        companyRepository.save(company);
    }

    public Company update(Integer targetCompanyID, Company updatedCompany) {
        Company company = companyRepository.findById(targetCompanyID).orElse(null);
        if (company == null) return null;
        company.setCompanyName(updatedCompany.getCompanyName());

        return companyRepository.save(company);
    }
}
