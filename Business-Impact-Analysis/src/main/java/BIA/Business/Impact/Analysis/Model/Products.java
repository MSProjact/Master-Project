package BIA.Business.Impact.Analysis.Model;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document (collection = "Products")
public class Products {
	@Id 
	    private int id;
	    private String P_Name;
	    private String P_Description;
	    private String P_Remarks;
	    public String Fun_Name;
	    public String ProductCategoryName;
	    public List<ProductionSteps> Functions;
	    public List<ProductCategory> ProductCategory;

	    

		public String getProductCategoryName() {
			return ProductCategoryName;
		}
		public void setProductCategoryName(String productCategoryName) {
			ProductCategoryName = productCategoryName;
		}
		public List<ProductCategory> getProductCategory() {
			return ProductCategory;
		}
		public void setProductCategory(List<ProductCategory> productCategory) {
			ProductCategory = productCategory;
		}
		public String getFun_Name() {
			return Fun_Name;
		}
		public void setFun_Name(String fun_Name) {
			Fun_Name = fun_Name;
		}
		public List<ProductionSteps> getFunctions() {
			return Functions;
		}
		public void setFunctions(List<ProductionSteps> functions) {
			Functions = functions;
		}
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
		
		public String getP_Remarks() {
			return P_Remarks;
		}
		public void setP_Remarks(String p_Remarks) {
			P_Remarks = p_Remarks;
		}	    
	  
}
