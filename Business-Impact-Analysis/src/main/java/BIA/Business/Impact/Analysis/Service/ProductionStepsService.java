package BIA.Business.Impact.Analysis.Service;

import BIA.Business.Impact.Analysis.Model.GenerateHierarchy;
import BIA.Business.Impact.Analysis.Model.ProductionSteps;
import BIA.Business.Impact.Analysis.Model.dtos.ProductStepsDto;
import BIA.Business.Impact.Analysis.Repository.ProductionStepsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductionStepsService {

    private final ProductionStepsRepository repo;

    private final GenerateHierarchyService generateHierarchyService;

    @Autowired
    public ProductionStepsService(ProductionStepsRepository repo, GenerateHierarchyService generateHierarchyService) {
        this.repo = repo;
        this.generateHierarchyService = generateHierarchyService;
    }


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

    public List<ProductStepsDto> getProductCategoryForCurrentEmployee() {
        List<GenerateHierarchy> generateHierarchy = generateHierarchyService.getGenerateHierarchyList();
        return ProductStepsDto.convertToDto(generateHierarchy);
    }
}

