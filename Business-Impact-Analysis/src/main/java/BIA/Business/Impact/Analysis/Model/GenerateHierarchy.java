package BIA.Business.Impact.Analysis.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Data
@AllArgsConstructor
@Document(collection = "GenerateHierarchy")
public class GenerateHierarchy {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	private String name;
	private int reportToId;
	private List<GenerateHierarchy> subordinates;
//	private int isRoot;

	public GenerateHierarchy(int id, String name, int reportToId, List<GenerateHierarchy> subordinates/* , int isRoot */) {
		super();
		this.id = id;
		this.name = name;
		this.reportToId = reportToId;
		this.subordinates = subordinates;
//			this.isRoot = isRoot;
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

//		public int getisRoot() {
//			return isRoot;
//		}
//		public void setisRoot(int isRoot) {
//			this.isRoot = isRoot;
//		}
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
