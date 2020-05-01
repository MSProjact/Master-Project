package BIA.Business.Impact.Analysis.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import BIA.Business.Impact.Analysis.Model.Resource;

public interface ResourcesRepository  extends  MongoRepository <Resource, String> {
    Resource findByProductId(String productId);
}
