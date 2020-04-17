package com.thoughtworks.springbootemployee.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Company {
    private int companyID;
    private String companyName;
    private List<Employee> employees = new ArrayList<>();
}
