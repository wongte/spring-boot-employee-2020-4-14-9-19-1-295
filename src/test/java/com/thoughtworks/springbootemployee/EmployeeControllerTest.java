package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.controller.EmployeeController;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import io.restassured.http.ContentType;
import io.restassured.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.spec.internal.HttpStatus;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Type;
import java.util.*;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeControllerTest {

    @MockBean
    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeController employeeController;

    private static final String EMPLOYEES_URL = "/employees";
    private TypeRef<List<Employee>> employeeListTypeRef;
    private Employee employee1, employee2;

    @Before
    public void setup() {
        employeeListTypeRef = new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        };
        RestAssuredMockMvc.standaloneSetup(employeeController);

        employee1 = new Employee(0, "Alice", 20, 1, 5000, "Female");
        employee2 = new Employee(1, "Bob", 1, 21, 5000, "Male");

        Company company = new Company();
        company.setCompanyID(1);
        company.setCompanyName("Apple");
        company.setEmployees(new ArrayList<>(Arrays.asList(employee1, employee2)));
    }

    @Test
    public void test_getEmployees_by_gender() {
        Mockito.when(employeeRepository.findAllByGender(Mockito.anyString())).thenReturn(Collections.singletonList(employee2));
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .param("gender", "Male")
                .when()
                .get(EMPLOYEES_URL);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        List<Employee> maleEmployees = response.getBody().as(employeeListTypeRef);
        Assert.assertEquals(1, maleEmployees.size());
        Assert.assertEquals("Bob", maleEmployees.get(0).getName());
    }

    @Test
    public void test_getEmployeesWithPaging_with_page() {
        Mockito.when(employeeRepository.findAll(Mockito.any(PageRequest.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(employee2)));
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .param("page", 2)
                .param("pageSize", 1)
                .when()
                .get(EMPLOYEES_URL);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        List<Employee> employees = response.getBody().as(employeeListTypeRef);
        Assert.assertEquals(1, employees.size());
        Assert.assertEquals("Bob", employees.get(0).getName());
    }

    @Test
    public void test_getAllEmployees_without_page() {
        Mockito.when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee1, employee2));
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get(EMPLOYEES_URL);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        List<Employee> employees = response.getBody().as(employeeListTypeRef);
        Assert.assertEquals(2, employees.size());
        Assert.assertEquals("Alice", employees.get(0).getName());
        Assert.assertEquals("Bob", employees.get(1).getName());
    }

    @Test
    public void test_getEmployeeById_when_give_id_return_employee() {
        int targetEmployeeID = 1;
        Mockito.when(employeeRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(employee2));
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get(EMPLOYEES_URL + "/" + targetEmployeeID);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        Employee resultEmployee = response.getBody().as(Employee.class);
        Assert.assertEquals(targetEmployeeID, resultEmployee.getId().intValue());
        Assert.assertEquals("Bob", resultEmployee.getName());
    }

    @Test
    public void test_create_employee() {
        Employee newEmployee = new Employee(3, "Cindy", 1, 21, 5000, "Female");

        Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(newEmployee);
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(newEmployee)
                .when()
                .post(EMPLOYEES_URL);

        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        Employee resultEmployee = response.getBody().as(Employee.class);

        Assert.assertEquals(newEmployee.getId(), resultEmployee.getId());
    }

    @Test
    public void test_update_employee() {
        Employee updatedAlice = new Employee(0, "Alice", 1, 25, 5000, "Female");
        updatedAlice.setCompanyID(1);

        Mockito.when(employeeRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(updatedAlice));
        Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(updatedAlice);
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(updatedAlice)
                .when()
                .put(EMPLOYEES_URL + "/" + updatedAlice.getId());

        Assert.assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());

        Employee resultEmployee = response.getBody().as(Employee.class);

        Assert.assertEquals(resultEmployee.getId(), resultEmployee.getId());
        Assert.assertEquals(updatedAlice.getAge(), resultEmployee.getAge());
    }

    @Test
    public void test_delete_employee() {
        int firedEmployeeID = 0;
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .delete(EMPLOYEES_URL + "/" + firedEmployeeID);

        Assert.assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }

}
