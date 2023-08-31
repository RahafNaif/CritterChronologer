package com.udacity.jdnd.course3.critter.pet;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class PetService {
    private final PetRepository petRepository;

    public PetDTO createPet(PetDTO petRequest) {
        Pet pet =  new Pet();
        BeanUtils.copyProperties(petRequest, pet);
        petRepository.save(petRequest);
    }
}
