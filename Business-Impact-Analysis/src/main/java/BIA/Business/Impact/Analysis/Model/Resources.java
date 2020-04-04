package BIA.Business.Impact.Analysis.Model;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document (collection = "Resources")
public class Resources {
	
	 @Id 
	   
	  
	
	  private String id;
	 
	    private int Cost_PH;
	    private int Cost_PM;
	    private int Cost_W;
	    private int RTO;
	    private int MTD;
	    private int Function_Recovery_Cost;
	    private int Function_Downtime_cost;
	    private int Function_rework_cost;
	    private int Function_parts_cost;
	    public List<ProductionSteps> ProductionSteps;
	    public String Function_name;
	    
	
	
	  public List<ProductionSteps> getProductionSteps() {
			return ProductionSteps;
		}

		public void setProductionSteps(List<ProductionSteps> productionSteps) {
			ProductionSteps = productionSteps;
		}

		public String getFunction_name() {
			return Function_name;
		}

		public void setFunction_name(String function_name) {
			Function_name = function_name;
		}

	
	 
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public int getFunction_Recovery_Cost() {
			return Function_Recovery_Cost;
		}
		public void setFunction_Recovery_Cost(int function_Recovery_Cost) {
			Function_Recovery_Cost = function_Recovery_Cost;
		}
		public int getFunction_Downtime_cost() {
			return Function_Downtime_cost;
		}
		public void setFunction_Downtime_cost(int function_Downtime_cost) {
			Function_Downtime_cost = function_Downtime_cost;
		}
	
		public int getFunction_rework_cost() {
			return Function_rework_cost;
		}
		public void setFunction_rework_cost(int function_rework_cost) {
			Function_rework_cost = function_rework_cost;
		}
		public int getFunction_parts_cost() {
			return Function_parts_cost;
		}
		public void setFunction_parts_cost(int function_parts_cost) {
			Function_parts_cost = function_parts_cost;
		}

	/*
	 * public int getId() { return id; }
	 */
	/*
	 * public void setId(int id) { this.id = id; }
	 */
		public int getCost_PH() {
			return Cost_PH;
		}
		public void setCost_PH(int cost_PH) {
			Cost_PH = cost_PH;
		}
		public int getCost_PM() {
			return Cost_PM;
		}
		public void setCost_PM(int cost_PM) {
			Cost_PM = cost_PM;
		}
		public int getCost_W() {
			return Cost_W;
		}
		public void setCost_W(int cost_W) {
			Cost_W = cost_W;
		}
		public int getRTO() {
			return RTO;
		}
		public void setRTO(int rTO) {
			RTO = rTO;
		}
		public int getMTD() {
			return MTD;
		}
		public void setMTD(int mTD) {
			MTD = mTD;
		}
	    
	    
}