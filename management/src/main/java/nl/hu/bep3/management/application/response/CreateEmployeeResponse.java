package nl.hu.bep3.management.application.response;

import nl.hu.bep3.management.domain.Employee;

public class CreateEmployeeResponse {

  public Employee employee;

  public static CreateEmployeeResponse of(final Employee employee) {
    final CreateEmployeeResponse response = new CreateEmployeeResponse();
    response.employee = employee;
    return response;
  }
}
