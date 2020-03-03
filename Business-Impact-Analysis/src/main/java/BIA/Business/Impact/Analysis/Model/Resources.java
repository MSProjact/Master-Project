package BIA.Business.Impact.Analysis.Model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document (collection = "Resources")
public class Resources {
	@Id 
	      private int id;
	    private int Cost_PH;
	    private int Cost_PM;
	    private int Cost_W;
	    private int RTO;
	    private int MTD;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
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
