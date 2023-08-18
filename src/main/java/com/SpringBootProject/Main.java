package com.SpringBootProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/customers")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class,args);
    }

    private final CustomerRepository customerRepository;
    public Main(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping
    public List<Customer> getCustomers(){
        return customerRepository.findAll();
    }

    record NewCustomerRequest(String name, String email, Integer age){

    }

    record UpdateCustomerRequest(String name, String email, Integer age){

    }
    @PostMapping
    public void addCustomer(@RequestBody NewCustomerRequest request){
        Customer customer = new Customer();
        customer.setName(request.name);
        customer.setEmail(request.email);
        customer.setAge(request.age);
        customerRepository.save(customer);
    }

    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Integer id){
        customerRepository.deleteById(id);
    }

    @PutMapping("{customerId}")
    public void updatingCustomer(@PathVariable("customerId") Integer customerId, @RequestBody UpdateCustomerRequest request) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);

        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            if (request.name != null) {
                customer.setName(request.name);
            }
            if (request.email != null) {
                customer.setEmail(request.email);
            }
            if (request.age != null) {
                customer.setAge(request.age);
            }
            customerRepository.save(customer);
        }
    }
//    @GetMapping("/greet")
//    public GreetResponse greet(){
//        GreetResponse response = new GreetResponse(
//                "hello",
//                List.of("Java","Python","JS"),
//                new Person("Manav")
//        );
//        return response;
//    }
//    record  Person (String name){}
//    record GreetResponse(String greet, List<String> ProgrammingLanguages, Person person){}


}