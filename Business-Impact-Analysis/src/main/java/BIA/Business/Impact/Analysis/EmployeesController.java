package BIA.Business.Impact.Analysis;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import org.springframework.stereotype.Controller;

@RestController
public class EmployeesController {
	
	@Autowired
	private EmployeesRepository repository;

	@PostMapping("/AddEmployeeDetails")
	public String saveEmployees(@RequestBody Employees Employees) {
		repository.save(Employees);
		return "Employee Details added : " + Employees.getId();
	}
	
	@GetMapping("/findEmployeesDetails")
	public List<Employees> getEmployeess() {
		return repository.findAll();
	}
	
	@GetMapping("/findEmployees/{id}")
	public Optional<Employees> getEmployees(@PathVariable int id) {
		return repository.findById(id);
	}

	@DeleteMapping("/delete/{id}")
	public String deleteEmployees(@PathVariable int id) {
		repository.deleteById(id);
		return "Employees with id : " + id;
	}
	
	
	

}
