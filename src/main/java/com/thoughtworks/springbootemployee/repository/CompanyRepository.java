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
        return CompanyInformationManager.getInstance().findCompanyByID(companyID);
    }

    public Company add(Company newCompany) {
        CompanyInformationManager.getInstance().addCompany(newCompany);
        return newCompany;
    }

    public void removeAllEmployees(int targetCompanyID) {
        CompanyInformationManager.getInstance().removeAllEmployeeInCompany(targetCompanyID);
    }

    public Company update(Integer targetCompanyID, Company updatedCompany) {
        updatedCompany.setCompanyID(targetCompanyID);
        CompanyInformationManager.getInstance().updateCompany(updatedCompany);
        return updatedCompany;
    }

    public List<Employee> findEmployees(int companyID) {
        return CompanyInformationManager.getInstance().findAllEmployeeInCompany(companyID);
    }
}
