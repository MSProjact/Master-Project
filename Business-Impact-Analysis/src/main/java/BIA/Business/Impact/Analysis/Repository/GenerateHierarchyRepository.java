package BIA.Business.Impact.Analysis.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import BIA.Business.Impact.Analysis.Model.GenerateHierarchy;

public interface GenerateHierarchyRepository  extends  MongoRepository <GenerateHierarchy, Integer> {

	

}
