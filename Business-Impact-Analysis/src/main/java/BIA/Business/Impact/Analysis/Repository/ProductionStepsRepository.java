package BIA.Business.Impact.Analysis.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import BIA.Business.Impact.Analysis.Model.ProductionSteps;

public interface ProductionStepsRepository  extends  MongoRepository <ProductionSteps, String> {

	

}
