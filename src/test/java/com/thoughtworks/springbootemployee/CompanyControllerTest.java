package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.controller.CompanyController;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.CompanyResponse;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import io.restassured.http.ContentType;
import io.restassured.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.spec.internal.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyControllerTest {

    private static final String COMPANIES_URL = "companies";

    @Mock
    CompanyService companyService;

    CompanyController companyController;

    List<Company> defaultCompanies;

    @Before
    public void setUp() {
        companyController = new CompanyController(companyService);
        RestAssuredMockMvc.standaloneSetup(companyController);

        CompanyInformationManager manager = CompanyInformationManager.getInstance();
        manager.reset();

        Employee employee1 = new Employee(0, "Alice", 20, "Female");
        employee1.setCompanyID(1);
        Employee employee2 = new Employee(1, "Bob", 21, "Male");
        employee2.setCompanyID(2);

        Company company1 = new Company();
        company1.setCompanyID(1);
        company1.setCompanyName("Company 1");
        company1.setEmployees(Collections.singletonList(employee1));

        Company company2 = new Company();
        company2.setCompanyID(2);
        company2.setCompanyName("Company 2");
        company2.setEmployees(Collections.singletonList(employee2));

        defaultCompanies = new ArrayList<>(Arrays.asList(company1, company2));
    }

    @Test
    public void test_get_all_companies_without_page() {
        Mockito.when(companyService.getCompanies()).thenReturn(defaultCompanies);
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get(COMPANIES_URL);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        List<CompanyResponse> companies = response.getBody().as(new TypeRef<List<CompanyResponse>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });

        Assert.assertEquals(2, companies.size());
    }

    @Test
    public void test_get_all_companies_with_page() {
        Mockito.when(companyService.getCompaniesWithPaging(Mockito.anyInt(), Mockito.anyInt())).thenReturn(defaultCompanies.subList(1, 2));
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .param("page", 2)
                .param("pageSize", 1)
                .when()
                .get(COMPANIES_URL);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        List<CompanyResponse> companies = response.getBody().as(new TypeRef<List<CompanyResponse>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });

        Assert.assertEquals(1, companies.size());
        Assert.assertEquals("Company 2", companies.get(0).getCompanyName());
    }

    @Test
    public void test_get_company_by_id() {
        Mockito.when(companyService.getCompanyByID(1)).thenReturn(defaultCompanies.get(0));
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get(COMPANIES_URL + "/" + 1);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        CompanyResponse resultCompany = response.getBody().as(CompanyResponse.class);
        Assert.assertEquals("Company 1", resultCompany.getCompanyName());
    }

    @Test
    public void test_get_employees_by_company_id() {
        Mockito.when(companyService.getCompanyEmployees(1)).thenReturn(defaultCompanies.get(0).getEmployees());
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get(COMPANIES_URL + "/" + 1 + "/employees");
        Assert.assertEquals(HttpStatus.OK, response.statusCode());
        List<Employee> employeesInCompany1 = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(1, employeesInCompany1.size());
        Assert.assertEquals("Alice", employeesInCompany1.get(0).getName());

    }

    @Test
    public void test_create_company() {
        Company newCompany = new Company();
        newCompany.setCompanyID(3);
        newCompany.setCompanyName("Company 3");
        Mockito.when(companyService.create(Mockito.any(Company.class))).thenReturn(newCompany);
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(newCompany)
                .when()
                .post(COMPANIES_URL);

        Assert.assertEquals(HttpStatus.CREATED, response.statusCode());
        CompanyResponse resultCompany = response.getBody().as(CompanyResponse.class);
        Assert.assertEquals(newCompany.getCompanyName(), resultCompany.getCompanyName());
    }

    @Test
    public void test_update_company() {
        String newCompany1Name = "Company 1 - new";
        Company updatedCompany1 = new Company();
        updatedCompany1.setCompanyID(1);
        updatedCompany1.setCompanyName(newCompany1Name);
        Mockito.when(companyService.update(Mockito.anyInt(), Mockito.any(Company.class))).thenReturn(updatedCompany1);
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(updatedCompany1)
                .when()
                .put(COMPANIES_URL + "/" + updatedCompany1.getCompanyID());

        Assert.assertEquals(HttpStatus.ACCEPTED, response.statusCode());
        CompanyResponse resultCompany = response.getBody().as(CompanyResponse.class);
        Assert.assertEquals(newCompany1Name, resultCompany.getCompanyName());
    }

    @Test
    public void test_remove_all_employees_by_company_id() {
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .delete(COMPANIES_URL + "/" + 1);
        Assert.assertEquals(HttpStatus.ACCEPTED, response.statusCode());
    }
}
