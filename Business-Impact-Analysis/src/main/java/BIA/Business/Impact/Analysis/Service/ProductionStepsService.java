package BIA.Business.Impact.Analysis.Service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import BIA.Business.Impact.Analysis.Model.ProductionSteps;
import BIA.Business.Impact.Analysis.Repository.ProductionStepsRepository;
@Service
@Transactional
public class ProductionStepsService {
	
	 @Autowired
	    private ProductionStepsRepository repo;
	     
	    public List<ProductionSteps> listAll() {
	        return repo.findAll();
	    }
	     
	    public void save(ProductionSteps ProductionSteps) {
	        repo.save(ProductionSteps);
	    }
	     
	    public ProductionSteps get(String id) {
	        return repo.findById(id).get();
	    }
	     
	    public void delete(String id) {
	        repo.deleteById(id);
	    }
}

