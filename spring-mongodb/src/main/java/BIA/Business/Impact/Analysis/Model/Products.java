package BIA.Business.Impact.Analysis.Model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document (collection = "Products")
public class Products {
	@Id 
	    private int id;
	    private String P_Name;
	    private String P_Description;
	    private String P_Type;
	    private String P_Remarks;

	    

		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}

	    public String getP_Name() {
			return P_Name;
		}
	    
		public void setP_Name(String p_Name) {
			P_Name = p_Name;
		}
		public String getP_Description() {
			return P_Description;
		}
		public void setP_Description(String p_Description) {
			P_Description = p_Description;
		}
		public String getP_Type() {
			return P_Type;
		}
		public void setP_Type(String p_Type) {
			P_Type = p_Type;
		}
		public String getP_Remarks() {
			return P_Remarks;
		}
		public void setP_Remarks(String p_Remarks) {
			P_Remarks = p_Remarks;
		}	    
	  
}
