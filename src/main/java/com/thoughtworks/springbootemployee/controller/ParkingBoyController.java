package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.ParkingBoy;
import com.thoughtworks.springbootemployee.service.ParkingBoyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking-boys")
public class ParkingBoyController {

    @Autowired
    private ParkingBoyService service;

    @GetMapping
    public List<ParkingBoy> getAll() {
        return service.getAll();
    }

    @PostMapping
    public ParkingBoy create(@RequestBody ParkingBoy parkingBoy) {
        return service.create(parkingBoy);
    }
}
