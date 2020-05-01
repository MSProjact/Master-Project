package BIA.Business.Impact.Analysis.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import BIA.Business.Impact.Analysis.Model.Product;

public interface ProductRepository extends  MongoRepository <Product, String> {

    List<Product> findByIdIn(List<String> productIds);
    List<Product> findByParentProductId(String id);
    List<Product> findByParentProductIdIsNull();
}
