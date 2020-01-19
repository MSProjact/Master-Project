import java.util.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

class EmployeeNode {
	int empId;
    String name;
    int reportToId;
    List<EmployeeNode> subordinates;
    
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
}

public class ReportToHierarchy {
	static Map<Integer, EmployeeNode> employees;
	static EmployeeNode root;
	    
	 public static void main(String[] args) throws IOException {
		    employees = new HashMap<Integer, EmployeeNode>();
	        readDataAndCreateMap();
	        buildHierarchyTree(root);
	        printHierarchyTree(root, 0);
	 }
	 
	private static void readDataAndCreateMap() throws IOException {
		final String dir = System.getProperty("user.dir");
        String path = dir.replace("/", "\\\\") + "\\data\\";
        
		try {
	        FileReader fin = new FileReader(path + "input-employee.txt");
			BufferedReader br = new BufferedReader(fin);
		        
			String strLine;
			EmployeeNode employee = null;

			while ((strLine = br.readLine()) != null) { 
				 String[] values = strLine.split(" ");
				 try {		 
					 if (values.length > 1) {		
			             employee = new EmployeeNode(values[0], values[1] + " " + values[2], values[3]);
					 }
				 } catch (Exception e) {
					 employee = new EmployeeNode(values[0], values[1] + " " + values[2], "0");
				 }
				 employees.put(employee.getId(), employee);
				 if (employee.getReportToId() == 0) {
	                 root = employee;
				 }
	        }
			fin.close();
	        br.close();
		} catch (FileNotFoundException e) {
			 System.err.println("FileNotFoundException: " + e);
		} catch (IOException e) {
			 System.err.println("IOException: " + e);
		}
	 }
	
	 //scan whole employee hashMap to form a list of subordinates for the given id
	 private static List<EmployeeNode> getSubordinatesById(int rid) {
		 List<EmployeeNode> subordinates = new ArrayList<EmployeeNode>();
		 for (EmployeeNode e : employees.values()) {
           if (e.getReportToId() == rid) {
           	subordinates.add(e);
           }
		 }
		 return subordinates;
	 }
	 	 
	 //build tree recursively
	 private static void buildHierarchyTree(EmployeeNode localRoot) {
		EmployeeNode employee = localRoot;
		List<EmployeeNode> subordinates = getSubordinatesById(employee.getId());
		employee.setSubordinates(subordinates);
        if (subordinates.size() == 0) {
            return;
        }

        for (EmployeeNode e : subordinates) {
        	buildHierarchyTree(e);
        }
	 }
	 
	 //print tree recursively
	 private static void printHierarchyTree(EmployeeNode localRoot, int level) {
		 for (int i = 0; i < level; i++) {
			 System.out.print("\t");
		 }		 
		 System.out.println(localRoot.getName());
		 
		 List<EmployeeNode> subordinates = localRoot.getSubordinates();
		 System.out.print(" ");
		 for (EmployeeNode e : subordinates) {
			 printHierarchyTree(e, level + 1);
		 }
	 }
}