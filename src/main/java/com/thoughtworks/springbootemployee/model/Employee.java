package com.thoughtworks.springbootemployee.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private int id;
    private String name;
    private int companyID;
    private int age;
    private int salary;
    private String gender;
}
