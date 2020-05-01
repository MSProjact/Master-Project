package BIA.Business.Impact.Analysis.Model.dtos;

import BIA.Business.Impact.Analysis.Model.GenerateHierarchy;
import BIA.Business.Impact.Analysis.Model.Product;
import BIA.Business.Impact.Analysis.Model.Resource;
import BIA.Business.Impact.Analysis.enums.ProductionType;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created at 11:34 PM, 10/04/2020
 */

public class ProductDto {
    String id;
    String name;
    String description;
    String funName;
    String remarks;
    String categoryName;
    List<ProductDto> subProducts;
    Resource resource;
    ProductDto parentProduct;
    Double mttr = 0D;
    List<ProductDto> parallelProducts;
    List<ProductDto> sequentialProducts;

    public ProductDto() {
    }

    public ProductDto(Product product, List<Product> siblings) {
        this.id = product.getId();
        this.name = product.getP_Name();
        this.description = product.getP_Description();
        this.funName = product.getFun_Name();
        this.remarks = product.getP_Remarks();
        this.categoryName = product.getProductCategoryName();
        this.resource = product.getResource();
        if (Objects.nonNull(product.getParentProduct())) {
            this.parentProduct = new ProductDto(product.getParentProduct(), null);
        }

        if (!CollectionUtils.isEmpty(siblings)) {
            this.parallelProducts = siblings.stream()
                    .filter(sibling -> Objects.nonNull(sibling)
                            && ProductionType.PARALLEL.equals(sibling.getProductType()))
                    .map(sibling -> new ProductDto(sibling, null))
                    .collect(Collectors.toList());

            this.sequentialProducts = siblings.stream()
                    .filter(sibling -> Objects.nonNull(sibling) &&
                            ProductionType.SEQUENTIAL.equals(sibling.getProductType()))
                    .map(sibling -> new ProductDto(sibling, null))
                    .collect(Collectors.toList());
        }

        if(Objects.nonNull(product.getResource())) {
            this.mttr = product.getResource().getFunction_rework_cost() > 0 ?
            ((double) product.getResource().getFunction_Downtime_cost() / (double) product.getResource().getFunction_rework_cost()) : 0D;
        }
    }

    public ProductDto(String id, String name, String description, String funName, String remarks, String categoryName, Resource resource) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.funName = funName;
        this.remarks = remarks;
        this.categoryName = categoryName;
        this.resource = resource;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFunName() {
        return funName;
    }

    public void setFunName(String funName) {
        this.funName = funName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<ProductDto> getSubProducts() {
        return subProducts;
    }

    public void setSubProducts(List<ProductDto> subProducts) {
        this.subProducts = subProducts;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public ProductDto getParentProduct() {
        return parentProduct;
    }

    public void setParentProduct(ProductDto parentProduct) {
        this.parentProduct = parentProduct;
    }

    public Double getMttr() {
        return mttr;
    }

    public void setMttr(Double mttr) {
        this.mttr = mttr;
    }

    public List<ProductDto> getSequentialProducts() {
        return sequentialProducts;
    }

    public void setSequentialProducts(List<ProductDto> sequentialProducts) {
        this.sequentialProducts = sequentialProducts;
    }

    public List<ProductDto> getParallelProducts() {
        return parallelProducts;
    }

    public void setParallelProducts(List<ProductDto> parallelProducts) {
        this.parallelProducts = parallelProducts;
    }

    /*
     * This is a DTO (Data Transfer Object) converter.
     * The idea is to keep the entities (models) away from
     * being exposed to the front-end (jsp/html) pages.
     *
     * Whenever a new key/field is needed to be exposed,
     * please add the key in this class and the method below.
     * */
    public static List<ProductDto> convertToDto(List<GenerateHierarchy> generateHierarchies) {
        List<ProductDto> products = new ArrayList<>();
        generateHierarchies.forEach(hierarchy ->
                Optional
                        .of(convertToDto(hierarchy))
                        .ifPresent(products::add));
        List<ProductDto> iteratedProducts = new ArrayList<>();
        products.forEach(productDto -> {
            if (Objects.nonNull(productDto)
                    && Objects.isNull(productDto.getId())
                    && !CollectionUtils.isEmpty(productDto.getSubProducts())) {
                iteratedProducts.addAll(productDto
                        .getSubProducts()
                        .stream()
                        .filter(Objects::nonNull).collect(Collectors.toList()));
            } else if (Objects.nonNull(productDto)
                    && Objects.nonNull(productDto.getId())) {
                iteratedProducts.add(productDto);
            }
        });
        return iteratedProducts;
    }

    public static ProductDto convertToDto(GenerateHierarchy hierarchy) {
        ProductDto productDto;
        Product product = hierarchy.getProduct();
        productDto = new ProductDto();
        if (Objects.nonNull(product)) {
            productDto.setId(product.getId());
            productDto.setName(product.getP_Name());
            productDto.setDescription(product.getP_Description());
            productDto.setFunName(product.getFun_Name());
            productDto.setRemarks(product.getP_Remarks());
            Optional.ofNullable(hierarchy.getResource()).ifPresent(productDto::setResource);
        }
        if (!CollectionUtils.isEmpty(hierarchy.getSubGenerateHierarchy())) {
            productDto.setSubProducts(convertToDto(hierarchy.getSubGenerateHierarchy()));
        }
        return productDto;
    }

    public static void calculateMean(List<ProductDto> productDtoList) {
        productDtoList.forEach(productDto -> {
            if (Objects.nonNull(productDto)) {
                if (Objects.nonNull(productDto.getParentProduct())) {
                    if (Objects.nonNull(productDto.getResource())) {
                        productDto.setMttr(productDto.getResource().getFunction_rework_cost() > 0 ? ((double) productDto.getResource().getFunction_Downtime_cost() / (double) productDto.getResource().getFunction_rework_cost()) : 0D);
                    }
                } else {
                    MeanInfo meanInfo = calculateMean(productDto.getSubProducts(), new MeanInfo(0D, 0));
                    productDto.setMttr(meanInfo.getMttr()/meanInfo.getCount());
                }
                if (!CollectionUtils.isEmpty(productDto.getSubProducts())) {
                    calculateMean(productDto.getSubProducts());
                }
            }
        });
    }

    public static MeanInfo calculateMean(List<ProductDto> subProducts, MeanInfo meanInfo) {
        if (!CollectionUtils.isEmpty(subProducts)) {
            for (ProductDto productDto : subProducts) {
                if (Objects.nonNull(productDto)) {
                    if (Objects.nonNull(productDto.getResource())) {
                        meanInfo.setMttr(meanInfo.getMttr() + productDto.getMttr());
                        meanInfo.setCount(meanInfo.getCount() + 1);
                    }
                    meanInfo = calculateMean(productDto.getSubProducts(), meanInfo);
                }
            }
        }
        return meanInfo;
    }

    public static class MeanInfo {
        Double mttr;
        Integer count;

        public MeanInfo(Double mttr, Integer count) {
            this.mttr = mttr;
            this.count = count;
        }

        public Double getMttr() {
            return mttr;
        }

        public void setMttr(Double mttr) {
            this.mttr = mttr;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }
    }
}
