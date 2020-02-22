package BIA.Business.Impact.Analysis.Model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Document (collection = "Employees")
public class Employees {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	    private int id;
	    private String First_name;
	    private String Last_name;
	    private String Designation;
	    private String Responsibility;
	    private String reportToid;
	    private List<Employees> subordinates;
		private int isRoot;
	   

		public int getId() {
			return id;
		}
		public List<Employees> getSubordinates() {
			return subordinates;
		}
		public void setSubordinates(List<Employees> subordinates) {
			this.subordinates = subordinates;
		}
		public int getIsRoot() {
			return isRoot;
		}
		public void setIsRoot(int isRoot) {
			this.isRoot = isRoot;
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
	  
		public String getReportToid() {
			return reportToid;
		}
		
		public void setReportToid(String reportToid) {
			this.reportToid = reportToid;
		}
		
	
}
