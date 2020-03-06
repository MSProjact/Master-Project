package BIA.Business.Impact.Analysis.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * 
 * 
 * here, In Employee model class we added the 2 new key named ReportToPerson and SunEmployees
 * and generated the getter setter method for it. Also, we updated the datatype for ReportToid with int eariler it was string. 
 */
@Document(collection = "Employees")
public class Employees {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	private String First_name;
	private String Last_name;
	private String Designation;
	private String Responsibility;
	private int ReportToid;
	private String ReportToPerson;
	private String Department;
	private int Phone_Number;
	private String Address;
	private String Email;
	public String getDepartment() {
		return Department;
	}

	public void setDepartment(String department) {
		Department = department;
	}

	public int getPhone_Number() {
		return Phone_Number;
	}

	public void setPhone_Number(int phone_Number) {
		Phone_Number = phone_Number;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	private List<Employees> SubEmployees;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirst_name() {
		return First_name;
	}

	public void setFirst_name(String first_name) {
		First_name = first_name;
	}

	public String getLast_name() {
		return Last_name;
	}

	public void setLast_name(String last_name) {
		Last_name = last_name;
	}

	public String getDesignation() {
		return Designation;
	}

	public void setDesignation(String designation) {
		Designation = designation;
	}

	public String getResponsibility() {
		return Responsibility;
	}

	public void setResponsibility(String responsibility) {
		Responsibility = responsibility;
	}

	public int getReportToid() {
		return ReportToid;
	}

	public void setReportToid(int reportToid) {
		ReportToid = reportToid;
	}
	
	public String getReportToPerson() {
		return ReportToPerson;
	}

	public void setReportToPerson(String reportToPerson) {
		ReportToPerson = reportToPerson;
	}

	public List<Employees> getSubEmployees() {
		return SubEmployees;
	}

	public void setSubEmployees(List<Employees> subEmployees) {
		SubEmployees = subEmployees;
	}
}
