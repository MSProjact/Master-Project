package BIA.Business.Impact.Analysis.Service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

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
	     
	    public ProductionSteps get(int id) {
	        return repo.findById(id).get();
	    }
	     
	    public void delete(int id) {
	        repo.deleteById(id);
	    }
}

