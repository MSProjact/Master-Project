package BIA.Business.Impact.Analysis.Service;

import BIA.Business.Impact.Analysis.Model.Employees;
import BIA.Business.Impact.Analysis.Model.GenerateHierarchy;
import BIA.Business.Impact.Analysis.Model.Product;
import BIA.Business.Impact.Analysis.Model.Role;
import BIA.Business.Impact.Analysis.Repository.DepartmentsRepository;
import BIA.Business.Impact.Analysis.Repository.EmployeesRepository;
import BIA.Business.Impact.Analysis.Repository.GenerateHierarchyRepository;
import BIA.Business.Impact.Analysis.Repository.ResourcesRepository;
import BIA.Business.Impact.Analysis.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class
GenerateHierarchyService {

    private final GenerateHierarchyRepository repo;

    private final EmployeesRepository employeeRepository;

    private final DepartmentsRepository departmentRepository;

    private final ResourcesRepository resourcesRepository;

    @Autowired
    public GenerateHierarchyService(GenerateHierarchyRepository repo,
                                    EmployeesRepository employeeRepository,
                                    DepartmentsRepository departmentRepository,
                                    ResourcesRepository resourcesRepository) {
        this.repo = repo;
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.resourcesRepository = resourcesRepository;
    }


    public List<GenerateHierarchy> listAll() {
        return repo.findAll();
    }

    public void save(GenerateHierarchy generateHierarchy) {
        generateHierarchy.setEmployee(employeeRepository.save(employeeRepository.findById(generateHierarchy.getId()).orElseThrow(() -> new RuntimeException("Invalid employee ID."))));
        generateHierarchy.setReportToEmployee(
                UserUtil.getCurrentUser().getId().equalsIgnoreCase(generateHierarchy.getId()) ?
                        generateHierarchy.getReportToEmployee() :
                        employeeRepository.findById(UserUtil.getCurrentUser().getId()).orElseThrow(() -> new RuntimeException("Invalid report-to ID.")));
        if (!StringUtils.isEmpty(generateHierarchy.getDepartment().getId())) {
            generateHierarchy.setDepartment(departmentRepository.findById(generateHierarchy.getDepartment().getId()).orElseThrow(() -> new RuntimeException("Invalid department ID")));
        }

        if (Objects.nonNull(generateHierarchy.getProduct()) && !StringUtils.isEmpty(generateHierarchy.getProduct().getId())) {
            generateHierarchy.setResource(resourcesRepository.findByProductId(generateHierarchy.getProduct().getId()));
        }

        repo.save(generateHierarchy);
    }

    public GenerateHierarchy get(String id) {
        return repo.findById(id).orElse(null);
    }

    public void delete(String id) {
        repo.deleteById(id);
    }

    public List<GenerateHierarchy> findByEmployeeIdIn(List<String> productIds) {
        return repo.findByEmployeeIdIn(productIds);
    }

    public GenerateHierarchy generateHierarchy(Employees employee) {
        GenerateHierarchy generateHierarchy = new GenerateHierarchy();
        generateHierarchy.setEmployee(employee);
        generateHierarchy.setReportToEmployeeId(UserUtil.getCurrentUser().getId());
        return repo.save(generateHierarchy);
    }


    /**
     * Gets the sub module.
     *
     * @param i            it takes the parent id into param for comparing with
     *                     child report to id.
     * @param employeeList in this list, all the employee list are available for
     *                     comparing the parent id with child report to id.
     * @return the employee model list who comes under the particular parent.
     * <p>
     * here, We called this method recursively for getting the tree
     * hierarchy
     */
    public List<GenerateHierarchy> getSubModule(String i, List<GenerateHierarchy> generateHierarchy) {
        List<GenerateHierarchy> subHierarchyList = new ArrayList<GenerateHierarchy>();
        if(StringUtils.isEmpty(i)) {
            subHierarchyList.addAll(generateHierarchy);
        } else {
            for (final GenerateHierarchy child : generateHierarchy) {
                if (Objects.nonNull(child.getReportToEmployee()) &&
                        i.equals(child.getReportToEmployee().getId())) {
                    subHierarchyList.add(child);
                }
            }
        }
        if (subHierarchyList.size() > 0) {
            for (final GenerateHierarchy subHierarchy : subHierarchyList) {
                List<GenerateHierarchy> childHierarchyList = getSubModule(subHierarchy.getId(),
                        generateHierarchy);
                subHierarchy.setSubGenerateHierarchy(childHierarchyList);
            }
        }
        return subHierarchyList;
    }

    public List<GenerateHierarchy> getGenerateHierarchyList() {
        List<GenerateHierarchy> generateHierarchyList = listAll();
        List<GenerateHierarchy> mainHierarchyList = new ArrayList<GenerateHierarchy>();
        Optional<GenerateHierarchy> generateHierarchyOptional = repo.findById(UserUtil.getCurrentUser().getId());
        if (generateHierarchyOptional.isPresent() || Role.ADMIN.equals(UserUtil.getCurrentUser().getRole())) {
            GenerateHierarchy currentUserHierarchy = generateHierarchyOptional.orElse(null);
            List<GenerateHierarchy> childHierarchyList = getSubModule(Objects.nonNull(currentUserHierarchy) ? currentUserHierarchy.getId() : null,
                    generateHierarchyList);
            if(Objects.nonNull(currentUserHierarchy)) {
                currentUserHierarchy.setSubGenerateHierarchy(childHierarchyList);
                mainHierarchyList.add(currentUserHierarchy);
            } else {
                mainHierarchyList.addAll(childHierarchyList);
            }
        }
        return mainHierarchyList;
    }

    public List<GenerateHierarchy> getGenerateHierarchyListForCurrentUser() {
        List<GenerateHierarchy> employeeList = new ArrayList<>();
        List<GenerateHierarchy> currentUserHierarchy = getGenerateHierarchyList();
        if (!CollectionUtils.isEmpty(currentUserHierarchy)) {
            employeeList.addAll(getSubHierarchyList(currentUserHierarchy));
        }
        return employeeList;
    }

    public List<GenerateHierarchy> getSubHierarchyList(List<GenerateHierarchy> generateHierarchy) {
        if (CollectionUtils.isEmpty(generateHierarchy)) {
            return Collections.EMPTY_LIST;
        }
        List<GenerateHierarchy> subHierarchy = new ArrayList<>();
        for (GenerateHierarchy hierarchy : generateHierarchy) {
            subHierarchy.add(hierarchy);
            subHierarchy.addAll(getSubHierarchyList(hierarchy.getSubGenerateHierarchy()));
        }
        return subHierarchy;
    }

    public GenerateHierarchy getForCurrentEmployee() {
        return repo.findByEmployeeIdIn(UserUtil.getCurrentUser().getId());
    }

    public List<GenerateHierarchy> getListForManageHierarchy() {
        if (UserUtil.isCurrentUserRole(Role.ADMIN)) {
            return listAll();
        }
        return getSubHierarchyList(getForCurrentEmployee());
    }

    public List<GenerateHierarchy> getSubHierarchyList(GenerateHierarchy generateHierarchy) {
        List<GenerateHierarchy> generateHierarchyList = getGenerateHierarchyListForCurrentUser();
        List<GenerateHierarchy> generateHierarchies = new ArrayList<>();
        if (!CollectionUtils.isEmpty(generateHierarchyList)) {
            generateHierarchies.addAll(generateHierarchyList);
        } else  if (Objects.nonNull(generateHierarchyList)) {
            generateHierarchies.add(generateHierarchy);
        }
        return generateHierarchies;
    }

    public GenerateHierarchy findByProductId(String productId) {
        return repo.findByProductId(productId);
    }

    public GenerateHierarchy findByRelatedProductId(String productId) {
        return repo.findByRelatedProductId(productId);
    }
}
