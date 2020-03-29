package BIA.Business.Impact.Analysis.Service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import BIA.Business.Impact.Analysis.Model.ProductCategory;
import BIA.Business.Impact.Analysis.Repository.ProductCategoryRepository;
@Service
@Transactional
public class ProductCategoryService {
	
	 @Autowired
	    private ProductCategoryRepository repo;
	     
	    public List<ProductCategory> listAll() {
	        return repo.findAll();
	    }
	     
	    public void save(ProductCategory ProductCategory) {
	        repo.save(ProductCategory);
	    }
	     
	    public ProductCategory get(int id) {
	        return repo.findById(id).get();
	    }
	     
	    public void delete(int id) {
	        repo.deleteById(id);
	    }
}

