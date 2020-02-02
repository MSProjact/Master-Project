package BIA.Business.Impact.Analysis.Resources;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

public interface ResourcesRepository  extends  MongoRepository <Resources, Integer> {

	

}
