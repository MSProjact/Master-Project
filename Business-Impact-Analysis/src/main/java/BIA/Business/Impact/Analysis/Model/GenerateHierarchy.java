package BIA.Business.Impact.Analysis.Model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Document (collection = "GenerateHierarchy")
public class GenerateHierarchy {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private int id;
    private String first_name;
    private String last_name;
    private String designation;
    private String responsibility;
	private int reportToId;
	private List<GenerateHierarchy> subordinates;
	private int isRoot;
	    

	
		public GenerateHierarchy(int id, String first_name, String last_name, String designation, String responsibility,
			int reportToId, List<GenerateHierarchy> subordinates, int isRoot) {
		super();
		this.id = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.designation = designation;
		this.responsibility = responsibility;
		this.reportToId = reportToId;
		this.subordinates = subordinates;
		this.isRoot = isRoot;
	}
		public List<GenerateHierarchy> getSubordinates() {
			return subordinates;
		}
		public void setSubordinates(List<GenerateHierarchy> subordinates) {
			this.subordinates = subordinates;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		
		public int getisRoot() {
			return isRoot;
		}
		public void setisRoot(int isRoot) {
			this.isRoot = isRoot;
		}
	
		public int getreportToId() {
			return reportToId;
		}
		public void setreportToId(int reportToId) {
			this.reportToId = reportToId;
		}
		public String getFirst_name() {
			return first_name;
		}
		public void setFirst_name(String first_name) {
			this.first_name = first_name;
		}
		public String getLast_name() {
			return last_name;
		}
		public void setLast_name(String last_name) {
			this.last_name = last_name;
		}
		public String getDesignation() {
			return designation;
		}
		public void setDesignation(String designation) {
			this.designation = designation;
		}
		public String getResponsibility() {
			return responsibility;
		}
		public void setResponsibility(String responsibility) {
			this.responsibility = responsibility;
		}





	 

}