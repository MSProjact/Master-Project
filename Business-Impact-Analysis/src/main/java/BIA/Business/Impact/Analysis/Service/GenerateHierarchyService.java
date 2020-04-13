package BIA.Business.Impact.Analysis.Service;

import BIA.Business.Impact.Analysis.Model.Employees;
import BIA.Business.Impact.Analysis.Model.GenerateHierarchy;
import BIA.Business.Impact.Analysis.Repository.DepartmentsRepository;
import BIA.Business.Impact.Analysis.Repository.EmployeesRepository;
import BIA.Business.Impact.Analysis.Repository.GenerateHierarchyRepository;
import BIA.Business.Impact.Analysis.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class GenerateHierarchyService {

    @Autowired
    private GenerateHierarchyRepository repo;

    @Autowired
    private EmployeesRepository employeeRepository;

    @Autowired
    private DepartmentsRepository departmentRepository;

    public List<GenerateHierarchy> listAll() {
        return repo.findAll();
    }

    public void save(GenerateHierarchy generateHierarchy) {
        Employees employee = employeeRepository.findById(generateHierarchy.getId()).orElseThrow(() -> new RuntimeException("Invalid employee ID."));
        employee.setReportToid(StringUtils.isEmpty(generateHierarchy.getReportToEmployeeId()) ? UserUtil.getCurrentUser().getId() : generateHierarchy.getReportToEmployeeId());
        generateHierarchy.setEmployee(employeeRepository.save(employee));
        if(!StringUtils.isEmpty(generateHierarchy.getDepartment().getId())) {
            generateHierarchy.setDepartment(departmentRepository.findById(generateHierarchy.getDepartment().getId()).orElseThrow(() -> new RuntimeException("Invalid department ID")));
        }
        repo.save(generateHierarchy);
    }

    public GenerateHierarchy get(String id) {
        return repo.findById(id).get();
    }

    public void delete(String id) {
        repo.deleteById(id);
        Employees employee = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Invalid employee ID"));
        employee.setReportToid(null);
        employeeRepository.save(employee);
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
        for (final GenerateHierarchy child : generateHierarchy) {
            if (Objects.nonNull(child.getEmployee()) && 
            		i.equals(child.getEmployee().getReportToid())) {
                subHierarchyList.add(child);
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
        List<GenerateHierarchy> generateHierarchy = listAll();
        List<GenerateHierarchy> mainHierarchyList = new ArrayList<GenerateHierarchy>();
        for (final GenerateHierarchy parentHierarchy : generateHierarchy) {
            if (parentHierarchy.getId().equals(UserUtil.getCurrentUser().getId())) {
                // if parentHierarchy is mine, add my sub-module
                List<GenerateHierarchy> childHierarchyList = getSubModule(parentHierarchy.getId(),
                        generateHierarchy);
                parentHierarchy.setSubGenerateHierarchy(childHierarchyList);
                mainHierarchyList.add(parentHierarchy);
            }
        }
        return mainHierarchyList;
    }
}
