package BIA.Business.Impact.Analysis.Service;

import BIA.Business.Impact.Analysis.Model.Departments;
import BIA.Business.Impact.Analysis.Model.GenerateHierarchy;
import BIA.Business.Impact.Analysis.Model.dtos.DepartmentDto;
import BIA.Business.Impact.Analysis.Repository.DepartmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Transactional
public class DepartmentsService {

    private final DepartmentsRepository repo;

    private final GenerateHierarchyService generateHierarchyService;

    @Autowired
    public DepartmentsService(DepartmentsRepository repo, GenerateHierarchyService generateHierarchyService) {
        this.repo = repo;
        this.generateHierarchyService = generateHierarchyService;
    }

    public List<Departments> listAll() {
        return repo.findAll();
    }

    public void save(Departments Departments) {
        repo.save(Departments);
    }

    public Departments get(String id) {
        return repo.findById(id).get();
    }

    public void delete(String id) {
        repo.deleteById(id);
    }

    public List<DepartmentDto> getDepartmentsForCurrentEmployee() {
        return DepartmentDto.convertToDto(generateHierarchyService.getGenerateHierarchyList());
    }

}

