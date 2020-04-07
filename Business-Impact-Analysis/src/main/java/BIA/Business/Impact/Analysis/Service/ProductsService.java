package BIA.Business.Impact.Analysis.Service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import BIA.Business.Impact.Analysis.Model.Products;
import BIA.Business.Impact.Analysis.Repository.ProductsRepository;
@Service
@Transactional
public class ProductsService {
	
	 @Autowired
	    private ProductsRepository repo;
	     
	    public List<Products> listAll() {
	        return repo.findAll();
	    }
	     
	    public void save(Products products) {
	        repo.save(products);
	    }
	     
	    public Products get(int id) {
	        return repo.findById(id).get();
	    }
	     
	    public void delete(int id) {
	        repo.deleteById(id);
	    }

		public void delete(String id) {
			// TODO Auto-generated method stub
			
		}

		public Products get(String id) {
			// TODO Auto-generated method stub
			return null;
		}
}

