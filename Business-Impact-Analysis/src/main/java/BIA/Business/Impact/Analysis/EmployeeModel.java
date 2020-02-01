package BIA.Business.Impact.Analysis;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document (collection = "Employees")
public class EmployeeModel {
	@Id 
	      private int id;
	    private String First_name;
	    private String Last_name;
	    private String Designation;
	    private String Responsibility;
	    private String Resources;
	    private int ReportToid;
	    private String ReportToDesignation;
	    
	    
		public int getId() {
			return id;
		}
		public int getReportToid() {
			return ReportToid;
		}
		public void setReportToid(int reportToid) {
			ReportToid = reportToid;
		}
		public String getReportToDesignation() {
			return ReportToDesignation;
		}
		public void setReportToDesignation(String reportToDesignation) {
			ReportToDesignation = reportToDesignation;
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
		public String getResources() {
			return Resources;
		}
		public void setResources(String resources) {
			Resources = resources;
		}
	
	    
	  
}
