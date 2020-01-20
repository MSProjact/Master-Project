package BIA.Business.Impact.Analysis;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document (collection = "Employees")
public class Employees {
	@Id 
	      private int id;
	    private String First_name;
	    private String Last_name;
	    private String Designation;
	    private String Responsibility;
	    private String Resources;
	    
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
		public String getResources() {
			return Resources;
		}
		public void setResources(String resources) {
			Resources = resources;
		}
	    
	  
}
