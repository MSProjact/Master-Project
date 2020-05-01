package BIA.Business.Impact.Analysis.Service;

import BIA.Business.Impact.Analysis.Model.Employees;
import BIA.Business.Impact.Analysis.Model.GenerateHierarchy;
import BIA.Business.Impact.Analysis.Model.Role;
import BIA.Business.Impact.Analysis.Repository.EmployeesRepository;
import BIA.Business.Impact.Analysis.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeesService {

    @Autowired
    private EmployeesRepository repo;

    @Autowired
    private GenerateHierarchyService generateHierarchyService;

    public List<Employees> listAllEmployeesForCurrentUser() {
        if (UserUtil.isCurrentUserRole(Role.ADMIN)) {
            return listAll();
        }
        return getSubEmployeesForEmployee(get(UserUtil.getCurrentUser().getId()));
    }

    public List<Employees> listAll() {
        return repo.findAll();
    }

    public void save(Employees employee) {
        repo.save(employee);
    }

    public Employees get(String id) {
        return repo.findById(id).get();
    }

    public void delete(String id) {
        repo.deleteById(id);
        generateHierarchyService.delete(id);
    }

    public List<Employees> getHierarchyForCurrentUser() {
        List<Employees> employees = Collections.EMPTY_LIST;
        GenerateHierarchy currentUserHierarchy = generateHierarchyService.get(UserUtil.getCurrentUser().getId());
        if (Objects.nonNull(currentUserHierarchy) || Role.ADMIN.equals(UserUtil.getCurrentUser().getRole())) {
            List<Employees> subEmployees = getSubModule(Objects.nonNull(currentUserHierarchy) ? currentUserHierarchy.getEmployee().getId() : null,
                    generateHierarchyService.listAll());
            employees = new ArrayList<>();
            if(Objects.nonNull(currentUserHierarchy)) {
                currentUserHierarchy.getEmployee().setSubEmployees(subEmployees);
                employees.add(currentUserHierarchy.getEmployee());
            } else {
                employees.addAll(subEmployees);
            }
        }

        return employees;
    }

    public List<Employees> getSubModule(String i, List<GenerateHierarchy> hierarchy) {
        List<Employees> subHierarchyList = new ArrayList<>();
        if(StringUtils.isEmpty(i)) {
            subHierarchyList.addAll(hierarchy.stream().map(GenerateHierarchy::getEmployee).collect(Collectors.toList()));
        } else {
            for (final GenerateHierarchy child : hierarchy) {
                if (Objects.nonNull(child)
                        && Objects.nonNull(child.getReportToEmployee())
                        && i.equals(child.getReportToEmployee().getId())) {
                    subHierarchyList.add(child.getEmployee());
                }
            }
        }
        if (subHierarchyList.size() > 0) {
            for (final Employees subHierarchy : subHierarchyList) {
                List<Employees> childHierarchyList = getSubModule(subHierarchy.getId(), hierarchy);
                subHierarchy.setSubEmployees(childHierarchyList);
            }
        }
        return subHierarchyList;
    }

    public List<Employees> getSubEmployeesForEmployee(Employees employees) {
        List<GenerateHierarchy> generateHierarchy = generateHierarchyService.getGenerateHierarchyListForCurrentUser();
        List<Employees> employeeList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(generateHierarchy)) {
            employeeList.addAll(generateHierarchy
                    .stream()
                    .map(GenerateHierarchy::getEmployee)
                    .collect(Collectors.toList()));
        } else {
            employeeList.add(employees);
        }
        return employeeList;
    }
}
