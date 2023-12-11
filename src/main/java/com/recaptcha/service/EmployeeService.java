package com.recaptcha.service;

import com.recaptcha.entities.EmployeeEntity;

import java.util.List;

public interface EmployeeService {

    List<EmployeeEntity> findAll();

    EmployeeEntity findById(Long id);

    void createEmployee(EmployeeEntity employee);

    void deleteEmployeeById(Long id);

}
