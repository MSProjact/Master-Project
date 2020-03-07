package BIA.Business.Impact.Analysis.Model;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Document (collection = "Resources")
public class Resources {
	@Id 
	      private int id;
	    private String Cost_PH;
	    private String Cost_PM;
	    private String Cost_W;
	    private String RTO;
	    private String MTD;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getCost_PH() {
			return Cost_PH;
		}
		public void setCost_PH(String cost_PH) {
			Cost_PH = cost_PH;
		}
		public String getCost_W() {
			return Cost_W;
		}
		public void setCost_W(String cost_W) {
			Cost_W = cost_W;
		}
		public String getCost_PM() {
			return Cost_PM;
		}
		public void setCost_PM(String cost_PM) {
			Cost_PM = cost_PM;
		}
		public String getRTO() {
			return RTO;
		}
		public void setRTO(String rTO) {
			RTO = rTO;
		}
		public String getMTD() {
			return MTD;
		}
		public void setMTD(String mTD) {
			MTD = mTD;
		}

}
