package BIA.Business.Impact.Analysis.Model;

import BIA.Business.Impact.Analysis.enums.ProductionType;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "GenerateHierarchy")
public class GenerateHierarchy {
	@Id
	private String id;
	@DBRef
	@Field("employee_ref")
	private Employees employee;
	@DBRef
	@Field("report_to_employee_ref")
	private Employees reportToEmployee;
	@Transient
	private String reportToEmployeeId;
	@DBRef
	@Field("product_ref")
	private Product product;
	private String productionStep;
	private String productCategory;
	@DBRef
	@Field("department_ref")
	private Departments department;
	private List<GenerateHierarchy> subGenerateHierarchy;

	@DBRef
	@Field("resource_ref")
	private Resource resource;

	@Field("production_type")
	private ProductionType productionType;

	@DBRef
	@Field("related_product")
	private Product relatedProduct;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Employees getEmployee() {
		return employee;
	}

	public void setEmployee(Employees employee) {
		this.employee = employee;
	}

	public Employees getReportToEmployee() {
		return reportToEmployee;
	}

	public void setReportToEmployee(Employees reportToEmployee) {
		this.reportToEmployee = reportToEmployee;
	}

	public String getReportToEmployeeId() {
		return reportToEmployeeId;
	}

	public void setReportToEmployeeId(String reportToEmployeeId) {
		this.reportToEmployeeId = reportToEmployeeId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getProductionStep() {
		return productionStep;
	}

	public void setProductionStep(String productionStep) {
		this.productionStep = productionStep;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public Departments getDepartment() {
		return department;
	}

	public void setDepartment(Departments department) {
		this.department = department;
	}

	public List<GenerateHierarchy> getSubGenerateHierarchy() {
		return subGenerateHierarchy;
	}

	public void setSubGenerateHierarchy(List<GenerateHierarchy> subGenerateHierarchy) {
		this.subGenerateHierarchy = subGenerateHierarchy;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public ProductionType getProductionType() {
		return productionType;
	}

	public void setProductionType(ProductionType productionType) {
		this.productionType = productionType;
	}

	public Product getRelatedProduct() {
		return relatedProduct;
	}

	public void setRelatedProduct(Product relatedProduct) {
		this.relatedProduct = relatedProduct;
	}
}
