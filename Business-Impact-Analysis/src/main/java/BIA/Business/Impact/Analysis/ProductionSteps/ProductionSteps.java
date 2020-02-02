package BIA.Business.Impact.Analysis.ProductionSteps;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document (collection = "ProductionSteps")
public class ProductionSteps {
	@Id 
	    private int id;
	    private String Funct_Name;
	    private String Key_Personals;
	    private String Criticality;
	    

		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getFunct_Name() {
			return Funct_Name;
		}
		public void setFunct_Name(String funct_Name) {
			Funct_Name = funct_Name;
		}
		public String getKey_Personals() {
			return Key_Personals;
		}
		public void setKey_Personals(String key_Personals) {
			Key_Personals = key_Personals;
		}
		public String getCriticality() {
			return Criticality;
		}
		public void setCriticality(String criticality) {
			Criticality = criticality;
		}


}
