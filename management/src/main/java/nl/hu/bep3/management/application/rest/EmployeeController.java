package nl.hu.bep3.management.application.rest;

import java.util.UUID;
import nl.hu.bep3.management.application.request.CreateEmployeeRequest;
import nl.hu.bep3.management.application.response.CreateEmployeeResponse;
import nl.hu.bep3.management.domain.Employee;
import nl.hu.bep3.management.domain.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
  private final EmployeeService employeeService;

  public EmployeeController(final EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @GetMapping()
  public Page<Employee> getCourseAssignments(final Pageable pageable) {
    return this.employeeService.findAllPaginated(pageable);
  }

  @PostMapping(
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  CreateEmployeeResponse createEmployee(
      @RequestBody final CreateEmployeeRequest createEmployeeRequest) {
    final Employee employee = this.employeeService.createEmployee(createEmployeeRequest.employee);
    return CreateEmployeeResponse.of(employee);
  }

  @DeleteMapping(value = "/{id}")
  HttpStatus deleteProduct(@PathVariable final UUID id) {
    this.employeeService.deleteEmployee(id);
    return HttpStatus.OK;
  }
}
