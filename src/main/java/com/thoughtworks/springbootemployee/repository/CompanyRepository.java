package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.CompanyInformationManager;
import com.thoughtworks.springbootemployee.ListUtility;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompanyRepository {
    public List<Company> findAll(Integer page, Integer pageSize) {
        List<Company> companies = CompanyInformationManager.getInstance().getCompanies();
        return new ListUtility<Company>().getListByPage(companies, page, pageSize);
    }

    public List<Company> findAll() {
        return CompanyInformationManager.getInstance().getCompanies();
    }

    public Company findByID(int companyID) {
        List<Company> allCompanies = CompanyInformationManager.getInstance().getCompanies();
        return allCompanies.stream()
                .filter(company -> company.getCompanyID() == companyID)
                .findAny()
                .orElse(null);
    }

    public Company add(Company newCompany) {
        CompanyInformationManager.getInstance().getCompanies().add(newCompany);
        return newCompany;
    }

    public Company update(Integer targetCompanyID, Company updatedCompany) {
        List<Company> companies = CompanyInformationManager.getInstance().getCompanies();
        for (int index = 0; index < companies.size(); index++) {
            if (companies.get(index).getCompanyID() == targetCompanyID) {
                companies.set(index, updatedCompany);
            }
        }
        return updatedCompany;
    }

    public List<Employee> findEmployees(int companyID) {
        return CompanyInformationManager.getInstance().findAllEmployeeInCompany(companyID);
    }
}
