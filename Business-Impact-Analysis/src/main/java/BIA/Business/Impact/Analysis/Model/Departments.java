package BIA.Business.Impact.Analysis.Model;

import java.util.List;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Departments")
public class Departments {
	@Id
	private String id;
	private String Dep_Name;
	private String Dep_Head;
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

	public String getDep_Name() {
		return Dep_Name;
	}

	public void setDep_Name(String dep_Name) {
		Dep_Name = dep_Name;
	}

	public String getDep_Head() {
		return Dep_Head;
	}

	public void setDep_Head(String dep_Head) {
		Dep_Head = dep_Head;
	}

}
