package BIA.Business.Impact.Analysis.Service;

import BIA.Business.Impact.Analysis.Model.Employees;
import BIA.Business.Impact.Analysis.Model.GenerateHierarchy;
import BIA.Business.Impact.Analysis.Model.Products;
import BIA.Business.Impact.Analysis.Repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductsService {

    private final ProductsRepository repo;

    private final EmployeesService employeesService;

    private final GenerateHierarchyService generateHierarchyService;

    @Autowired
    public ProductsService(ProductsRepository repo, EmployeesService employeesService, GenerateHierarchyService generateHierarchyService) {
        this.repo = repo;
        this.employeesService = employeesService;
        this.generateHierarchyService = generateHierarchyService;
    }

    public List<Products> listAll() {
        return repo.findAll();
    }

    public void save(Products products) {
        repo.save(products);
    }

    public Products get(String id) {
        return repo.findById(id).get();
    }

    public void delete(String id) {
        repo.deleteById(id);
    }

    public List<Products> getProductsForCurrentEmployee() {
         List<String> productNames = getSubHierarchies(generateHierarchyService.getGenerateHierarchyList())
                .stream().map(GenerateHierarchy::getProduct)
                .collect(Collectors.toList());
                  
         generateHierarchyService.getGenerateHierarchyList()
                .forEach(s -> System.out.print(s.getId() + ' ' + s.getProduct()));
         System.out.print(productNames);
         return listAll()
        		 .stream()
        		 .filter(product -> 
        		 !CollectionUtils.isEmpty(productNames) 
        		 && productNames.contains(product.getP_Name()))
        		 .collect(Collectors.toList());
         
    }
    
    public List<GenerateHierarchy> getSubHierarchies(List<GenerateHierarchy> hierarchies) {
        List<GenerateHierarchy> subHierarchies = new ArrayList<>();
        if(hierarchies == null) {
            return Collections.EMPTY_LIST;
        }
        subHierarchies.addAll(hierarchies);
        for(GenerateHierarchy hierarchy : hierarchies) {
        	subHierarchies.addAll(getSubHierarchies(hierarchy.getSubGenerateHierarchy()));
        }
        return subHierarchies;
    }
}

