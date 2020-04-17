package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.ParkingBoy;
import com.thoughtworks.springbootemployee.repository.ParkingBoyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingBoyService {
    @Autowired
    private ParkingBoyRepository repository;

    public List<ParkingBoy> getAll() {
        return repository.findAll();
    }

    public ParkingBoy create(ParkingBoy parkingBoy) {
        return repository.save(parkingBoy);
    }
}
