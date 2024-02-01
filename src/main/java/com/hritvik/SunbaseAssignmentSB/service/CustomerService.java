package com.hritvik.SunbaseAssignmentSB.service;



import com.hritvik.SunbaseAssignmentSB.model.Customer;
import com.hritvik.SunbaseAssignmentSB.model.dto.CustomerDto;
import com.hritvik.SunbaseAssignmentSB.model.dto.LoginDto;
import com.hritvik.SunbaseAssignmentSB.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class CustomerService {

    @Value("${external.api.url}")
    private String externalApiUrl;

    @Value("${external.auth.url}")
    private String externalAuthUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    CustomerRepository customerRepository;

    public static String generateUniqueId() {
        // Generate a random UUID
        UUID randomUUID = UUID.randomUUID();

        // Extract relevant portions of the UUID
        String randomPart = randomUUID.toString().replaceAll("-", "");

        // Combine with the fixed prefix
        return "test" + randomPart;
    }
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }


    public Customer getCustomerById(String id) {
        return customerRepository.findById(id).orElse(null);
    }


    public void saveCustomer(Customer customer) {
        if(customer.getId() == null) {
            customer.setId(generateUniqueId());
        }
        customerRepository.save(customer);
    }


    public void deleteCustomer(String id) {
        customerRepository.deleteById(id);
    }


    public String authenticate(String loginId, String password) {

        LoginDto loginDto = new LoginDto();
        loginDto.setLogin_id(loginId);
        loginDto.setPassword(password);
        return restTemplate.postForObject(externalAuthUrl, loginDto, String.class);

    }


    public List<Customer> syncCustomers(String token) {

        HttpHeaders headers = new HttpHeaders();

        String substring = token.substring(19, token.length() - 3);
        System.out.println(substring);
        headers.setBearerAuth(substring);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(externalApiUrl)
                .queryParam("cmd", "get_customer_list");

//        RequestCallback requestCallback = restTemplate.httpEntityCallback(null, Customer.class);

        ResponseEntity<CustomerDto[]> customers = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                CustomerDto[].class
        );

          for (CustomerDto customerList : Objects.requireNonNull(customers.getBody())) {
              Customer customer= new Customer();
              customer.setId(customerList.getUuid());
              customer.setAddress(customerList.getAddress());
              customer.setCity(customerList.getCity());
              customer.setPhone(customerList.getPhone());
              customer.setStreet(customerList.getStreet());
              customer.setLastName(customerList.getLast_name());
              customer.setState(customerList.getState());
              customer.setFirstName(customerList.getFirst_name());
              customer.setEmail(customerList.getEmail());
                     
              saveCustomer(customer);
          }
//        customerRepository.saveAll(Arrays.asList(Objects.requireNonNull(customers.getBody())));

        return customerRepository.findAll();
    }


    public List<Customer> searchCustomers(String keyword) {
        return customerRepository.findByFirstNameContainingOrLastNameContainingOrEmailContainingOrIdContainingOrPhoneContaining(keyword, keyword, keyword, keyword, keyword);
    }


    public List<Customer> sortCustomers(String sortBy) {
        return switch (sortBy) {
            case "firstName" -> customerRepository.findAll(Sort.by(Sort.Order.asc("firstName")));
            case "city" -> customerRepository.findAll(Sort.by(Sort.Order.asc("city")));
            case "email" -> customerRepository.findAll(Sort.by(Sort.Order.asc("email")));
            case "phone no" -> customerRepository.findAll(Sort.by(Sort.Order.asc("phone")));
            default -> customerRepository.findAll();
        };
    }

//    public Page<Customer> paginateCustomers(int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        return customerRepository.findAll(pageable);
//    }


    public void updateCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public Page<Customer> getPaginatedCustomers(PageRequest of) {
        return customerRepository.findAll(of);
    }
}
