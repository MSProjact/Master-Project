package BIA.Business.Impact.Analysis.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Column;
import java.util.List;

@Document(collection = "GenerateHierarchy")
public class GenerateHierarchy {
	private String id;

	@DBRef
	@Field("employee_ref")
	private Employees employee;
	@Transient
	private String reportToEmployeeId;
	private String product;
	private String productionStep;
	private String productCategory;
	@DBRef
	@Field("department_ref")
	private Departments department;
	private List<GenerateHierarchy> subGenerateHierarchy;

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

	public String getReportToEmployeeId() {
		return reportToEmployeeId;
	}

	public void setReportToEmployeeId(String reportToEmployeeId) {
		this.reportToEmployeeId = reportToEmployeeId;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
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
}
