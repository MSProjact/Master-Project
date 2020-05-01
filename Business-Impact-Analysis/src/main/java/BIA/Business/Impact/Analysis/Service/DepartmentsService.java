package BIA.Business.Impact.Analysis.Service;

import BIA.Business.Impact.Analysis.Model.Departments;
import BIA.Business.Impact.Analysis.Model.GenerateHierarchy;
import BIA.Business.Impact.Analysis.Model.Product;
import BIA.Business.Impact.Analysis.Model.Role;
import BIA.Business.Impact.Analysis.Model.dtos.DepartmentDto;
import BIA.Business.Impact.Analysis.Repository.DepartmentsRepository;
import BIA.Business.Impact.Analysis.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public List<Departments> getListForManageDepartments() {
        if (UserUtil.isCurrentUserRole(Role.ADMIN)) {
            return listAll();
        }
        GenerateHierarchy hierarchy = generateHierarchyService.getForCurrentEmployee();
        Departments departments = null;
        if(Objects.nonNull(hierarchy)) {
            departments = hierarchy.getDepartment();
        }
        return getSubDepartment(departments);
    }

    public List<Departments> getSubDepartment(Departments department) {
        List<GenerateHierarchy> generateHierarchy = generateHierarchyService.getGenerateHierarchyListForCurrentUser();
        List<Departments> departments = new ArrayList<>();
        if (!CollectionUtils.isEmpty(generateHierarchy)) {
            departments.addAll(generateHierarchy
                    .stream()
                    .filter(hierarchy -> Objects.nonNull(hierarchy.getDepartment()))
                    .map(GenerateHierarchy::getDepartment)
                    .collect(Collectors.toList()));
        } else  if (Objects.nonNull(department)) {
            departments.add(department);
        }
        return departments;
    }

}

