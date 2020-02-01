package BIA.Business.Impact.Analysis.Resources;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import BIA.Business.Impact.Analysis.Employees;

public interface ResourcesRepository  extends  MongoRepository <Resources, Integer> {

	

}
