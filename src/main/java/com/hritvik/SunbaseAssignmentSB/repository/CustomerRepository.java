package com.hritvik.SunbaseAssignmentSB.repository;


import com.hritvik.SunbaseAssignmentSB.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,String> {

    List<Customer> findByFirstNameContainingOrLastNameContainingOrEmailContainingOrIdContainingOrPhoneContaining(String keyword, String keyword1, String keyword2, String keyword3, String keyword4);
}
