package BIA.Business.Impact.Analysis.Model.dtos;

import BIA.Business.Impact.Analysis.Model.GenerateHierarchy;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Created at 11:34 PM, 10/04/2020
 */

public class ProductStepsDto {
    String name;
    String employeeName;
    List<ProductStepsDto> subSteps;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public List<ProductStepsDto> getSubSteps() {
        return subSteps;
    }

    public void setSubSteps(List<ProductStepsDto> subSteps) {
        this.subSteps = subSteps;
    }

    /*
    * This is a DTO (Data Transfer Object) converter.
    * The idea is to keep the entities (models) away from
    * being exposed to the front-end (jsp/html) pages.
    *
    * Whenever a new key/field is needed to be exposed,
    * please add the key in this class and the method below.
    * */
    public static List<ProductStepsDto> convertToDto(List<GenerateHierarchy> generateHierarchies) {
        List<ProductStepsDto> productSteps = new ArrayList<>();
        generateHierarchies.forEach(hierarchy -> productSteps.add(convertToDto(hierarchy)));
        return productSteps;
    }

    public static ProductStepsDto convertToDto(GenerateHierarchy hierarchy) {
        ProductStepsDto productStepsDto = new ProductStepsDto();
        productStepsDto.setName(hierarchy.getProductionStep());
        productStepsDto.setEmployeeName(hierarchy.getEmployee().getFullName());
        if(hierarchy.getSubGenerateHierarchy() != null) {
            productStepsDto.setSubSteps(convertToDto(hierarchy.getSubGenerateHierarchy()));
        }
       return productStepsDto;
    }
}
