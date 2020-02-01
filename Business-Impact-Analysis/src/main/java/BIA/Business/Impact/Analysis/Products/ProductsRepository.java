package BIA.Business.Impact.Analysis.Products;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

public interface ProductsRepository  extends  MongoRepository <Products, Integer> {

	

}
