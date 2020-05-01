package BIA.Business.Impact.Analysis.Service;

import BIA.Business.Impact.Analysis.Model.GenerateHierarchy;
import BIA.Business.Impact.Analysis.Model.Product;
import BIA.Business.Impact.Analysis.Model.Role;
import BIA.Business.Impact.Analysis.Model.dtos.ProductDto;
import BIA.Business.Impact.Analysis.Repository.ProductRepository;
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
public class ProductsService {

    private final ProductRepository repo;

    private final EmployeesService employeesService;

    private final GenerateHierarchyService generateHierarchyService;

    private final ResourcesRepository resourcesRepository;

    private List<String> visitedSiblings = new ArrayList<>();

    private List<String> visitedProducts = new ArrayList<>();

    @Autowired
    public ProductsService(ProductRepository repo, EmployeesService employeesService,
                           GenerateHierarchyService generateHierarchyService, ResourcesRepository resourcesRepository) {
        this.repo = repo;
        this.employeesService = employeesService;
        this.generateHierarchyService = generateHierarchyService;
        this.resourcesRepository = resourcesRepository;
    }

    public List<Product> listAll() {
        return repo.findAll();
    }

    public List<Product> getProductListForManageProducts() {
        if (UserUtil.isCurrentUserRole(Role.ADMIN)) {
            return listAll();
        }
        GenerateHierarchy hierarchy = generateHierarchyService.getForCurrentEmployee();
        Product product = null;
        if (Objects.nonNull(hierarchy)) {
            product = hierarchy.getProduct();
        }
        return getSubProducts(product);
    }

    public List<Product> getRelatedProductsListForCurrentUser() {
        List<Product> products;

        GenerateHierarchy currentGenerateHierarchy = generateHierarchyService.getForCurrentEmployee();
        if (Objects.nonNull(currentGenerateHierarchy) && Objects.nonNull(currentGenerateHierarchy.getProduct())) {
            Product currentProduct = currentGenerateHierarchy.getProduct();
            products = new ArrayList<>(Collections.singletonList(currentProduct));
            products.addAll(repo.findByParentProductId(currentProduct.getId()));
        } else {
            products = listAll();
        }

        return products;
    }

    public List<Product> getProductsListForCurrentUser() {
        return getRelatedProductsListForCurrentUser().stream().filter(product -> Objects.isNull(generateHierarchyService.findByProductId(product.getId()))).collect(Collectors.toList());
    }

    public void save(Product product) {
        if (Objects.nonNull(product.getParentProduct())
                && StringUtils.isEmpty(product.getParentProduct().getId())) {
            product.setParentProduct(null);
        }
        repo.save(product);
    }

    public Product get(String id) {
        return repo.findById(id).get();
    }

    public void delete(String id) {
        repo.deleteById(id);
    }

    public List<ProductDto> getProductsForCurrentEmployee() {
        return ProductDto.convertToDto(generateHierarchyService.getGenerateHierarchyList());
    }

    public List<Product> getSubProducts(Product product) {
        List<GenerateHierarchy> generateHierarchy = generateHierarchyService.getGenerateHierarchyListForCurrentUser();
        List<Product> products = new ArrayList<>();
        if (!CollectionUtils.isEmpty(generateHierarchy)) {
            products.addAll(generateHierarchy
                    .stream()
                    .filter(hierarchy -> Objects.nonNull(hierarchy.getProduct()))
                    .map(GenerateHierarchy::getProduct)
                    .collect(Collectors.toList()));
        } else if (Objects.nonNull(product)) {
            products.add(product);
        }
        return products;
    }

    public List<ProductDto> productAnalysisHierarchy() {
        // Get the list of all parent products.
        List<ProductDto> productDtoList = buildProductAnalysisHierarchy(repo.findByParentProductIdIsNull());


        // Calculate mean RTO and put populate it
        // in each parent for its children
        ProductDto.calculateMean(productDtoList);
        productDtoList = productDtoList.stream().map(productDto -> this.retainUnique(productDto, productDto)).filter(Objects::nonNull).collect(Collectors.toList());
        this.visitedSiblings.clear();
        this.visitedProducts.clear();
        return productDtoList;
    }

    public List<ProductDto> buildProductAnalysisHierarchy(List<Product> products) {
        List<ProductDto> productDtoList = new ArrayList<>();

        // Get the list of product ID's from the current hierarchy
        // that can be displayed to the current user.
        List<String> productIdsForCurrentEmployee = getProductListForManageProducts()
                .stream()
                .map(Product::getId)
                .filter(product -> Objects.nonNull(generateHierarchyService.findByProductId(product)))
                .collect(Collectors.toList());

        // For each parent product, find the child products,
        // and put them in the list.
        products.forEach(product -> {
            if (Objects.nonNull(product) && productIdsForCurrentEmployee.contains(product.getId())) {
                product.setResource(resourcesRepository.findByProductId(product.getId()));

                GenerateHierarchy generateHierarchy = generateHierarchyService.findByProductId(product.getId());
                Product relatedProduct = Objects.nonNull(generateHierarchy) ?
                        generateHierarchy.getRelatedProduct() : null;
                if (Objects.nonNull(generateHierarchy) && Objects.nonNull(relatedProduct)) {
                    relatedProduct.setProductType(generateHierarchy.getProductionType());
                }
                List<Product> siblings = getSiblings(relatedProduct, productIdsForCurrentEmployee);
                if (!CollectionUtils.isEmpty(siblings)) {
                    this.visitedSiblings.addAll(siblings.stream().map(Product::getId).collect(Collectors.toList()));
                }
                ProductDto productDto = new ProductDto(product, siblings);

                List<Product> subProducts = repo.findByParentProductId(product.getId());
                if (!CollectionUtils.isEmpty(subProducts)) {
                    productDto.setSubProducts(buildProductAnalysisHierarchy(subProducts));
                }
                //Mark the product (irrespective if child or parent) as visited, in order to avoid redundancy.
                if (!this.visitedProducts.contains(product.getId())) {
                    this.visitedProducts.add(productDto.getId());
                    productDtoList.add(productDto);
                }
            }
        });
        return productDtoList;
    }

    //Get the siblings for the current product, recursive method
    public List<Product> getSiblings(Product relatedProduct, List<String> productIdsForCurrentEmployee) {
        List<Product> siblings = null;
        if (Objects.nonNull(relatedProduct)) {
            siblings = new ArrayList<>();
            Product currentRelatedProduct = relatedProduct;
            while (Objects.nonNull(currentRelatedProduct) && !this.visitedSiblings.contains(currentRelatedProduct.getId())) {
                currentRelatedProduct.setResource(resourcesRepository.findByProductId(currentRelatedProduct.getId()));
                if(productIdsForCurrentEmployee.contains(currentRelatedProduct.getId())) {
                    siblings.add(currentRelatedProduct);
                }
                GenerateHierarchy generateHierarchy = generateHierarchyService.findByProductId(currentRelatedProduct.getId());
                currentRelatedProduct = Objects.nonNull(generateHierarchy) ? Optional
                        .ofNullable(generateHierarchy.getRelatedProduct())
                        .orElse(null) : null;

                if (Objects.nonNull(generateHierarchy) && Objects.nonNull(currentRelatedProduct)) {
                    currentRelatedProduct.setProductType(generateHierarchy.getProductionType());
                }
            }
        }

        return siblings;
    }

    //Filter all the duplicates for the current product and keep unique only
    public ProductDto retainUnique(ProductDto productDto, ProductDto parentProductDto) {
        List<String> duplicates = this.visitedSiblings.stream().filter(this.visitedProducts::contains).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(duplicates)) {
            // If the product is duplicated, move its children below in the hierarchy
            // and remove itself from the root.
            if (duplicates.contains(productDto.getId())) {
                moveDuplicate(productDto, parentProductDto);
                return null;
            }
            if (!CollectionUtils.isEmpty(productDto.getSubProducts())) {
                productDto.setSubProducts(productDto.getSubProducts().stream().map(product -> this.retainUnique(product, parentProductDto)).filter(Objects::nonNull).collect(Collectors.toList()));
            }
        }

        System.out.println();
        return productDto;
    }

    //Move all duplicates to child products by removing parent product
    public void moveDuplicate(ProductDto duplicateProductDto, ProductDto parentProductDto) {
        List<ProductDto> siblingProducts = new ArrayList<>();
        if(!CollectionUtils.isEmpty(parentProductDto.getParallelProducts()))
        siblingProducts.addAll(parentProductDto.getParallelProducts());

        if(!CollectionUtils.isEmpty(parentProductDto.getSequentialProducts()))
        siblingProducts.addAll(parentProductDto.getSequentialProducts());

        if(siblingProducts.stream().map(ProductDto::getId).collect(Collectors.toList()).contains(duplicateProductDto.getId())) {
            if(!CollectionUtils.isEmpty(duplicateProductDto.getParallelProducts()))
            parentProductDto.getParallelProducts().addAll(duplicateProductDto.getParallelProducts());
            if(!CollectionUtils.isEmpty(duplicateProductDto.getSequentialProducts()))
            parentProductDto.getSequentialProducts().addAll(duplicateProductDto.getSequentialProducts());
            return;
        }

        if(!CollectionUtils.isEmpty(parentProductDto.getSubProducts())) {
            parentProductDto.getSubProducts().forEach(productDto -> this.moveDuplicate(duplicateProductDto, productDto));
        }

    }
}

