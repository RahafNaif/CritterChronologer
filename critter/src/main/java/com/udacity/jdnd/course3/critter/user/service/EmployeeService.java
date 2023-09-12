package com.udacity.jdnd.course3.critter.user.service;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.dto.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.entity.Customer;
import com.udacity.jdnd.course3.critter.user.entity.Employee;
import com.udacity.jdnd.course3.critter.user.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeDTO createEmployee(EmployeeDTO employeeRequest) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeRequest, employee);
        this.employeeRepository.save(employee);
        employeeRequest.setId(employee.getId());
        return employeeRequest;
    }

    public EmployeeDTO getEmployee(long employeeId) {
        return mapToEmployeeDTO(this.employeeRepository.findById(employeeId).get());
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        Employee employee = this.employeeRepository.findById(employeeId).get();
        employee.setDaysAvailable(daysAvailable);
        this.employeeRepository.save(employee);
    }

    public List<EmployeeDTO> findEmployeesForService(EmployeeRequestDTO employeeDTO) {
        List<Employee> employees = this.employeeRepository.findByDaysAvailableContaining(employeeDTO.getDate().getDayOfWeek());
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();

        employees.forEach((employee -> {
            if (employee.getSkills().containsAll(employeeDTO.getSkills())) {
                employeeDTOList.add(mapToEmployeeDTO(employee));
            }
        }));
        return employeeDTOList;
    }

    private EmployeeDTO mapToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }
}
