package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.CompanyInformationManager;
import com.thoughtworks.springbootemployee.ListUtility;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeRepository {
    public List<Employee> findAll() {
        return CompanyInformationManager.getInstance().getEmployees();
    }

    public List<Employee> findByGender(String gender) {
        return CompanyInformationManager.getInstance().getEmployeesByGender(gender);
    }

    public List<Employee> findAll(Integer page, Integer pageSize) {
        List<Employee> employees = CompanyInformationManager.getInstance().getEmployees();
        return new ListUtility<Employee>().getListByPage(employees, page, pageSize);
    }

    public Employee findByID(int employeeID) {
        return CompanyInformationManager.getInstance().findEmployeeByID(employeeID);
    }

    public Employee add(Employee employee) {
        CompanyInformationManager.getInstance().addEmployee(employee);
        return employee;
    }

    public void delete(Integer targetEmployeeID) {
        CompanyInformationManager.getInstance().deleteEmployee(targetEmployeeID);
    }

    public Employee update(Integer targetEmployeeID, Employee updatedEmployee) {
        updatedEmployee.setId(targetEmployeeID);
        CompanyInformationManager.getInstance().updateEmployee(updatedEmployee);
        return updatedEmployee;
    }
}
