package com.thoughtworks.springbootemployee.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int companyID;
    private String companyName;

    @OneToMany(targetEntity = Employee.class, fetch = FetchType.EAGER, mappedBy = "companyID")
    private List<Employee> employees = new ArrayList<>();
}
