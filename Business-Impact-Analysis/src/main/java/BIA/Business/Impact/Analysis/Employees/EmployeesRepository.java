package BIA.Business.Impact.Analysis.Employees;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

public interface EmployeesRepository  extends  MongoRepository <Employees, Integer> {

	

}
