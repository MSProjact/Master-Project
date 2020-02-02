package BIA.Business.Impact.Analysis.ProductionSteps;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import BIA.Business.Impact.Analysis.Products.Products;

public interface ProductionStepsRepository  extends  MongoRepository <ProductionSteps, Integer> {

	

}
