package BIA.Business.Impact.Analysis;

import java.util.List;

public class EmployeeNode {
	public int empId;
    public String name;
    public int reportToId;
    public List<EmployeeNode> subordinates;
    
    public EmployeeNode(String id,  String empName, String rid) {    
    	try {
	        this.empId = Integer.parseInt(id);	        
	        this.name = empName;
	        this.reportToId = Integer.parseInt(rid);
    	}  catch (Exception e) {
			 System.err.println("Exception creating employee:" + e);
		}
    }

    List<EmployeeNode> getSubordinates() {
        return subordinates;
    }
    
    void setSubordinates(List<EmployeeNode> subordinates) {
        this.subordinates = subordinates;
    }
    
    int getId() {
        return empId;
    }

    void setId(int id) {
        this.empId = id;
    }
    
    String getName() {
        return name;
    }

    void setName(String n) {
        name = n;
    }

    int getReportToId() {
        return reportToId;
    }

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public void setReportToId(int reportToId) {
		this.reportToId = reportToId;
	}
    
    
}
