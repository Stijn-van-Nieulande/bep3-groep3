package nl.hu.bep3.management.domain.service;

import nl.hu.bep3.management.domain.Employee;

import java.util.UUID;

public interface EmployeeService {
  UUID createEmployee(Employee employee);
}
