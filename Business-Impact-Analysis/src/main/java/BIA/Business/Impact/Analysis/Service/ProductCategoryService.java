package BIA.Business.Impact.Analysis.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import BIA.Business.Impact.Analysis.Model.Employees;
import BIA.Business.Impact.Analysis.Model.GenerateHierarchy;
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

	 @Autowired
	 private GenerateHierarchyService generateHierarchyService;

	 @Autowired
	 private EmployeesService employeesService;
	     
	    public List<ProductCategory> listAll() {
	        return repo.findAll();
	    }
	     
	    public void save(ProductCategory ProductCategory) {
	        repo.save(ProductCategory);
	    }
	     
	    public ProductCategory get(String id) {
	        return repo.findById(id).get();
	    }
	     
	    public void delete(String id) {
	        repo.deleteById(id);
	    }

	    public List<ProductCategory> getProductCategoryForCurrentEmployee() {
			return repo.findByCategoryNameIn(generateHierarchyService.findByEmployeeIdIn(employeesService
					.getSubEmployeesForCurrentUser()
					.stream()
					.map(Employees::getId)
					.collect(Collectors.toList()))
					.stream()
					.map(GenerateHierarchy::getProductCategory)
					.collect(Collectors.toList()));
		}
}

