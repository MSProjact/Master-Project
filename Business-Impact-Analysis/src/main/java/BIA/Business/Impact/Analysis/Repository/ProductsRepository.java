package BIA.Business.Impact.Analysis.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import BIA.Business.Impact.Analysis.Model.Products;

public interface ProductsRepository  extends  MongoRepository <Products, Integer> {

	

}
