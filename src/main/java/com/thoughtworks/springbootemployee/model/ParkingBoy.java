package com.thoughtworks.springbootemployee.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ParkingBoy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nickName;

    private Integer employeeId;

    @OneToOne
    @JoinColumn(name = "employeeId", insertable = false, updatable = false)
    private Employee employee;
}
