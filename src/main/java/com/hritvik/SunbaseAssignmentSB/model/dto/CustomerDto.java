package com.hritvik.SunbaseAssignmentSB.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto {

    private String uuid;
    private String address;
    private String city;
    private String phone;
    private String street;
    private String last_name;
    private String state;
    private String first_name;
    private String email;
}
