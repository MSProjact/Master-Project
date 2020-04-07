package BIA.Business.Impact.Analysis.Model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ProductionSteps")
public class ProductionSteps {
	@Id
	private String id;
	private String Funct_Name;
	private String Criticality;
	public String ReportTo;
	public List<Employees> employees;

	public List<Employees> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employees> employees) {
		this.employees = employees;
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFunct_Name() {
		return Funct_Name;
	}

	public void setFunct_Name(String funct_Name) {
		Funct_Name = funct_Name;
	}

	public String getCriticality() {
		return Criticality;
	}

	public void setCriticality(String criticality) {
		Criticality = criticality;
	}

	public String getReportTo() {
		return ReportTo;
	}

	public void setReportTo(String reportTo) {
		ReportTo = reportTo;
	}

}