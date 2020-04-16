package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    EmployeeRepository employeeRepository;

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
        List<Employee> employeesInCompany = companyRepository.findEmployees(targetCompanyID);

        company.setEmployeesNumber(0);
        companyRepository.update(targetCompanyID, company);

        employeesInCompany.forEach(employee -> employeeRepository.delete(employee.getId()));
    }

    public Company update(Integer targetCompanyID, Company updatedCompany) {
        return companyRepository.update(targetCompanyID, updatedCompany);
    }
}
