package BIA.Business.Impact.Analysis.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import BIA.Business.Impact.Analysis.Model.Employees;

public interface EmployeesRepository extends MongoRepository<Employees, String> {

}

