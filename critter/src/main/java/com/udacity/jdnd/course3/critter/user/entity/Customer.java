package com.udacity.jdnd.course3.critter.user.entity;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.dto.CustomerDTO;
import lombok.Builder;
import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table
@Data
public class Customer extends BaseUser {
    private String phoneNumber;
    private String notes;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private List<Pet> pets;
}
