package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.ParkingBoy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingBoyRepository extends JpaRepository<ParkingBoy, Integer> {
}
