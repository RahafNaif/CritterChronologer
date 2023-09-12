package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.entity.Customer;
import com.udacity.jdnd.course3.critter.user.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class PetService {
    private final PetRepository petRepository;
    private final CustomerService customerService;

    public PetDTO createPet(PetDTO petRequest) {
        Pet pet =  new Pet();
        BeanUtils.copyProperties(petRequest, pet);
        Customer customer = customerService.getCustomer(petRequest.getOwnerId());
        pet.setCustomer(customer);
        petRepository.save(pet);
        petRequest.setId(pet.getId());
        this.customerService.addPet(pet.getCustomer(), pet);
        return petRequest;
    }

    public PetDTO getPet(Long id) {
        return mapToPetDTO(petRepository.findById(id).get());
    }

    public List<PetDTO> getPets() {
        List<PetDTO> petDTOList = new ArrayList<>();
        List<Pet> pets = this.petRepository.findAll();
        pets.forEach((pet) -> {
            petDTOList.add(mapToPetDTO(pet));
        });
        return petDTOList;
    }

    public List<PetDTO> getPetsByOwner(long ownerId) {
        List<PetDTO> petDTOList = new ArrayList<>();
        List<Pet> pets = this.petRepository.findAllByCustomerId(ownerId);
        pets.forEach((pet) -> {
            petDTOList.add(mapToPetDTO(pet));
        });
        return petDTOList;
    }

    private PetDTO mapToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setOwnerId(pet.getCustomer().getId());
        return petDTO;
    }
}
