package com.hritvik.SunbaseAssignmentSB.controller;

import com.hritvik.SunbaseAssignmentSB.model.Customer;
import com.hritvik.SunbaseAssignmentSB.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import org.hibernate.action.internal.EntityAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.AuthenticationException;
import java.util.List;


@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session ,@RequestParam( defaultValue = "0") int page,@RequestParam( defaultValue = "10") int size) {

        Page<Customer> customerPage = customerService.getPaginatedCustomers(PageRequest.of(page, size));
        model.addAttribute("customers", customerPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", customerPage.getTotalPages());
        String token = (String) session.getAttribute("token");
        model.addAttribute(token);
//        List<Customer> customers = customerService.getAllCustomers();
//        model.addAttribute("customers", customers);
        return "dashboard";
    }

    @GetMapping("/customer/{id}")
    public String viewCustomer(@PathVariable String id, Model model) {
        Customer customer = customerService.getCustomerById(id);
        model.addAttribute("customer", customer);
        return "customer-details";
    }

    @GetMapping("/add-customer")
    public String showAddCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "add-customer";
    }

    @PostMapping("/add-customer")
    public String addCustomer(@ModelAttribute Customer customer) {
        customerService.saveCustomer(customer);
        return "redirect:/dashboard";
    }

    @GetMapping("/update-customer/{id}")
    public String showUpdateCustomerForm(@PathVariable String id, Model model) {
        Customer customer = customerService.getCustomerById(id);
        model.addAttribute("customer", customer);
        return "update-customer";
    }

    @PostMapping("/update-customer")
    public String updateCustomer(@ModelAttribute Customer customer) {
        customerService.updateCustomer(customer);
        return "redirect:/dashboard";
    }

    @GetMapping("/delete-customer/{id}")
    public String deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomer(id);
        return "redirect:/dashboard";
    }

    @PostMapping("/authenticate")
    public String authenticate(@RequestParam String loginId, HttpSession session, @RequestParam String password, Model model, RedirectAttributes redirectAttributes) {

           String token = customerService.authenticate(loginId, password);
           // Save the token to the session or use it as needed
           model.addAttribute("token", token);
           session.setAttribute("token", token);
           return "redirect:/dashboard";


    }

    @GetMapping("/sync-customers")
    public String syncCustomers( Model model, HttpSession session,RedirectAttributes redirectAttributes) {
        session.getAttribute("token");

        String token = (String) session.getAttribute("token");
        System.out.println(token);
        model.addAttribute("token", token);
        List<Customer> syncedCustomers = customerService.syncCustomers(token);
        model.addAttribute("syncedCustomers", syncedCustomers);
        redirectAttributes.addFlashAttribute("syncedCustomers", syncedCustomers);
        return "redirect:/dashboard";
    }

    @PostMapping("/search-customers")
    public String searchCustomers(@RequestParam String keyword, Model model) {
        List<Customer> searchedCustomers = customerService.searchCustomers(keyword);
        model.addAttribute("customers", searchedCustomers);
        return "dashboard";
    }

    @GetMapping("/sort-customers")
    public String sortCustomers(@RequestParam String sortBy, Model model) {
        List<Customer> sortedCustomers = customerService.sortCustomers(sortBy);
        model.addAttribute("customers", sortedCustomers);
        return "dashboard";
    }

    @ExceptionHandler(AuthenticationException.class)
    public String handleAuthenticationException(AuthenticationException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "/";
    }

//    @GetMapping("/paginate-customers")
//    public String paginateCustomers(@RequestParam(defaultValue = "0") int page,
//                                    @RequestParam(defaultValue = "10") int size,
//                                    Model model) {
//        Page<Customer> customerPage = customerService.paginateCustomers(page, size);
//        model.addAttribute("customers", customerPage.getContent());
//        model.addAttribute("currentPage", page);
//        model.addAttribute("totalPages", customerPage.getTotalPages());
//        return "dashboard";
//    }

}


