package BIA.Business.Impact.Analysis;
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
	    private String name;
	    public int reportToId;
	    public List<GenerateHierarchy> subordinates;
	    

		public GenerateHierarchy(int id, String name, int reportToId, List<GenerateHierarchy> subordinates) {
			super();
			this.id = id;
			this.name = name;
			this.reportToId = reportToId;
			this.subordinates = subordinates;
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
		public int getReportToId() {
			return reportToId;
		}



		public void setReportToId(int reportToId) {
			this.reportToId = reportToId;
		}



		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	
	 

}
