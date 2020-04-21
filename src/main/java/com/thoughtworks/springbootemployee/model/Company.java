package com.thoughtworks.springbootemployee.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String companyName;

    @OneToMany(targetEntity = Employee.class, fetch = FetchType.EAGER, mappedBy = "companyID")
    private List<Employee> employees = new ArrayList<>();
}
