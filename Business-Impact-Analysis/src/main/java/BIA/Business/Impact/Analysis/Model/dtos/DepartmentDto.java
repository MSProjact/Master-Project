package BIA.Business.Impact.Analysis.Model.dtos;

import BIA.Business.Impact.Analysis.Model.Departments;
import BIA.Business.Impact.Analysis.Model.GenerateHierarchy;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author SafeerAnsari
 * Created at 1:10 AM, 11/04/2020
 */

public class DepartmentDto {

    private String name;
    private String head;
    private List<DepartmentDto> subDepartments;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public List<DepartmentDto> getSubDepartments() {
        return subDepartments;
    }

    public void setSubDepartments(List<DepartmentDto> subDepartments) {
        this.subDepartments = subDepartments;
    }

    /*
     * This is a DTO (Data Transfer Object) converter.
     * The idea is to keep the entities (models) away from
     * being exposed to the front-end (jsp/html) pages.
     *
     * Whenever a new key/field is needed to be exposed,
     * please add the key in this class and the method below.
     * */
    public static List<DepartmentDto> convertToDto(List<GenerateHierarchy> generateHierarchies) {
        List<DepartmentDto> departmentList = new ArrayList<>();
        generateHierarchies.forEach(hierarchy -> {
            DepartmentDto departmentDto = convertToDto(hierarchy);
            if(Objects.nonNull(departmentDto)) {
                boolean containsDepartmentAlready = Boolean.FALSE;
                for (DepartmentDto department : departmentList) {
                    if (department.getName().equalsIgnoreCase(departmentDto.getName())) {
                        containsDepartmentAlready = Boolean.TRUE;
                        break;
                    }
                }
                if (!containsDepartmentAlready) {
                    departmentList.add(departmentDto);
                }
            }
        });
        return departmentList;
    }

    public static DepartmentDto convertToDto(GenerateHierarchy hierarchy) {
        DepartmentDto departmentDto = null;
        if (Objects.nonNull(hierarchy.getDepartment())) {
            departmentDto = new DepartmentDto();
            departmentDto.setName(hierarchy.getDepartment().getDep_Name());
            departmentDto.setHead(hierarchy.getDepartment().getDep_Head());
            if (hierarchy.getSubGenerateHierarchy() != null) {
                departmentDto.setSubDepartments(convertToDto(hierarchy.getSubGenerateHierarchy()));
            }
        }
        return departmentDto;
    }

}
