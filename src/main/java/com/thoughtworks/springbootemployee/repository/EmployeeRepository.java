package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findAllByGender(String gender);
//    public List<Employee> findAll() {
//        return CompanyInformationManager.getInstance().getEmployees();
//    }
//
//    public List<Employee> findByGender(String gender) {
//        List<Employee> allEmployees = CompanyInformationManager.getInstance().getEmployees();
//        return allEmployees.stream().filter(employee -> employee.getGender().equals(gender)).collect(Collectors.toList());
//    }
//
//    public List<Employee> findAll(Integer page, Integer pageSize) {
//        List<Employee> employees = CompanyInformationManager.getInstance().getEmployees();
//        return new ListUtility<Employee>().getListByPage(employees, page, pageSize);
//    }
//
//    public Employee findByID(int employeeID) {
//        List<Employee> allEmployees = CompanyInformationManager.getInstance().getEmployees();
//        return allEmployees.stream()
//                .filter(employee -> employee.getId() == employeeID)
//                .findAny()
//                .orElse(null);
//    }
//
//    public Employee add(Employee employee) {
//        Company targetCompany = CompanyInformationManager.getInstance().findCompanyByID(employee.getCompanyID());
//        targetCompany.getEmployees().add(employee);
//        return employee;
//    }
//
//    public void delete(Integer targetEmployeeID) {
//        Employee firedEmployee = this.findByID(targetEmployeeID);
//        Company company = CompanyInformationManager.getInstance().findCompanyByID(firedEmployee.getCompanyID());
//        company.getEmployees().removeIf(employee -> employee.getId() == targetEmployeeID);
//    }
//
//    public Employee update(Integer targetEmployeeID, Employee updatedEmployee) {
//        List<Employee> allEmployees = CompanyInformationManager.getInstance().getEmployees();
//        Optional<Employee> targetEmployeeOptional = allEmployees.stream().filter(employee -> employee.getId() == targetEmployeeID).findAny();
//
//        if (!targetEmployeeOptional.isPresent()) return null;
//
//        Employee employee = targetEmployeeOptional.get();
//        employee.setAge(updatedEmployee.getAge());
//        employee.setGender(updatedEmployee.getGender());
//        employee.setName(updatedEmployee.getName());
//        employee.setSalary(updatedEmployee.getSalary());
//        return employee;
//    }
}
