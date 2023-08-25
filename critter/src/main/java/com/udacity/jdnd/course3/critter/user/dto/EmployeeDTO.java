package com.udacity.jdnd.course3.critter.user.dto;

import com.udacity.jdnd.course3.critter.user.enums.EmployeeSkill;
import lombok.Data;
import java.time.DayOfWeek;
import java.util.Set;

@Data
public class EmployeeDTO {
    private long id;
    private String name;
    private Set<EmployeeSkill> skills;
    private Set<DayOfWeek> daysAvailable;
}
