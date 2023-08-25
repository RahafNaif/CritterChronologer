package com.udacity.jdnd.course3.critter.user.dto;

import com.udacity.jdnd.course3.critter.user.enums.EmployeeSkill;
import lombok.Data;
import java.time.LocalDate;
import java.util.Set;

@Data
public class EmployeeRequestDTO {
    private Set<EmployeeSkill> skills;
    private LocalDate date;
}
