package com.hritvik.SunbaseAssignmentSB.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Customer {

    @Id
    private String Id;
    private String address;
    private String city;
    private String phone;
    private String street;
    @Column(name = "last_name")
    private String lastName;
    private String state;
    @Column(name = "first_name")
    private String firstName;
    private String email;


}
