package BIA.Business.Impact.Analysis.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import BIA.Business.Impact.Analysis.Model.Resources;

public interface ResourcesRepository  extends  MongoRepository <Resources, Integer> {

	Optional<Resources> findById(String id);

	void deleteById(String id);

	

}
