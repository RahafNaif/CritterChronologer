package com.udacity.jdnd.course3.critter.user.service;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.user.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.entity.Customer;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PetRepository petRepository;

    public CustomerDTO createCustomer(CustomerDTO customerRequest) {
        List<Pet> pets = new ArrayList<>();
        if (customerRequest.getPetIds() != null) {
            customerRequest.getPetIds().forEach((pet) -> {
                pets.add(petRepository.findById(pet).get());
            });
        }
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerRequest, customer);
        customer.setPets(pets);
        System.out.print(customer);
        customerRepository.save(customer);
        return mapToCustomerDTO(customer);
    }

    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        customers.forEach((customer) -> {
            customerDTOList.add(mapToCustomerDTO(customer));
        });
        return customerDTOList;
    }
    //test after pet done
    public CustomerDTO getOwnerByPet(long id) {
        Customer customer = customerRepository.findByPetsId(id);
        return mapToCustomerDTO(customer);
    }

    private CustomerDTO mapToCustomerDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        List<Long> petIds = new ArrayList<>();
        List<Pet> pets = customer.getPets();

        BeanUtils.copyProperties(customer, customerDTO);
        if (pets != null) {
            pets.forEach((pet) -> {
                petIds.add(pet.getId());
            });
            customerDTO.setPetIds(petIds);
        }

        return customerDTO;
    }
}
