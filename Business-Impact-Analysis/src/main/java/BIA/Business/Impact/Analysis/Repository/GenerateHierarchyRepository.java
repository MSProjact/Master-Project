package BIA.Business.Impact.Analysis.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import BIA.Business.Impact.Analysis.GenerateHierarchy;
import BIA.Business.Impact.Analysis.Model.Employees;

public interface GenerateHierarchyRepository  extends  MongoRepository <GenerateHierarchy, Integer> {

	

}
