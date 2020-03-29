package BIA.Business.Impact.Analysis.Model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "GenerateHierarchy")
public class GenerateHierarchy {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EmployeeId")
	private int employeeId;
	private String employeeName;
	private int reportToEmployeeId;
	private String product;
	private String productionStep;
	private String productCategory;
	private String department;
	private List<GenerateHierarchy> subGenerateHierarchy;
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public int getReportToEmployeeId() {
		return reportToEmployeeId;
	}
	public void setReportToEmployeeId(int reportToEmployeeId) {
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
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public List<GenerateHierarchy> getSubGenerateHierarchy() {
		return subGenerateHierarchy;
	}
	public void setSubGenerateHierarchy(List<GenerateHierarchy> subGenerateHierarchy) {
		this.subGenerateHierarchy = subGenerateHierarchy;
	}
	
}
