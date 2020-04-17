package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.controller.ParkingBoyController;
import com.thoughtworks.springbootemployee.model.ParkingBoy;
import com.thoughtworks.springbootemployee.repository.ParkingBoyRepository;
import io.restassured.http.ContentType;
import io.restassured.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.spec.internal.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParkingBoyControllerTest {

    @MockBean
    ParkingBoyRepository repository;

    @Autowired
    ParkingBoyController parkingBoyController;

    private static List<ParkingBoy> parkingBoys;

    @BeforeClass
    public static void beforeClass() {
        ParkingBoy parkingBoy1 = new ParkingBoy();
        parkingBoy1.setId(1);
        parkingBoy1.setNickName("nick1");
        ParkingBoy parkingBoy2 = new ParkingBoy();
        parkingBoy2.setId(2);
        parkingBoy2.setNickName("nick2");
        parkingBoys = Arrays.asList(parkingBoy1, parkingBoy2);
    }

    @Before
    public void setUp() {
        RestAssuredMockMvc.standaloneSetup(parkingBoyController);
    }

    @Test
    public void test_get_all_parking_boys() {
        Mockito.when(repository.findAll()).thenReturn(parkingBoys);
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/parking-boys");

        Assert.assertEquals(HttpStatus.OK, response.statusCode());

        List<ParkingBoy> resultParkingBoys = response.as(new TypeRef<List<ParkingBoy>>() {});
        Assert.assertEquals(parkingBoys.size(), resultParkingBoys.size());
        Assert.assertEquals(parkingBoys.get(0).getId(), resultParkingBoys.get(0).getId());
        Assert.assertEquals(parkingBoys.get(0).getNickName(), resultParkingBoys.get(0).getNickName());
    }

    @Test
    public void test_create_parking_boy() {
        ParkingBoy newParkingBoy = parkingBoys.get(0);
        Mockito.when(repository.save(Mockito.any(ParkingBoy.class))).thenReturn(newParkingBoy);

        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(newParkingBoy)
                .when()
                .post("/parking-boys");

        Assert.assertEquals(HttpStatus.CREATED, response.statusCode());

        ParkingBoy resultParkingBoy = response.as(ParkingBoy.class);
        Assert.assertEquals(newParkingBoy.getNickName(), newParkingBoy.getNickName());

    }
}
