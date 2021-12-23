package nl.hu.bep3.management.application.response;

import nl.hu.bep3.management.domain.Employee;

public class PatchEmployeeResponse {
  public Employee employee;

  public static PatchEmployeeResponse of(final Employee employee) {
    final PatchEmployeeResponse response = new PatchEmployeeResponse();
    response.employee = employee;
    return response;
  }
}
