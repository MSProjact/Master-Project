package BIA.Business.Impact.Analysis.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import BIA.Business.Impact.Analysis.Model.GenerateHierarchy;

import java.util.List;

public interface GenerateHierarchyRepository extends MongoRepository<GenerateHierarchy, String> {
    List<GenerateHierarchy> findByEmployeeIdIn(List<String> productList);

}
