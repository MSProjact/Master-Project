package BIA.Business.Impact.Analysis.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import BIA.Business.Impact.Analysis.Model.GenerateHierarchy;
import BIA.Business.Impact.Analysis.Service.GenerateHierarchyService;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

@Controller("/GenerateHierarchyController")
public class GenerateHierarchyController {

	@Autowired
	private GenerateHierarchyService service;
	
	static GenerateHierarchy root;
	static String root_user = "faisal";
	public int level;

	@RequestMapping("/Display")
	
	public String initTree(Model model) {
			
		List<GenerateHierarchy> EmpHierarchy = service.listAll();
			
			model.addAttribute("Employeelist", EmpHierarchy.get(0));
		    
			
			return "indexGenerateHierarchy.html";
	        
			
	 }
	

	
	 private static List<GenerateHierarchy> getSubordinatesById(int empId,Map<Integer, GenerateHierarchy> employees) {
		 List<GenerateHierarchy> subordinates = new ArrayList<GenerateHierarchy>();
		 for (GenerateHierarchy e : employees.values()) {
           if (e.getreportToId() == empId) {
           	subordinates.add(e);
           }
		 }
		 return subordinates;
	 }
	 	 
	 //build tree recursively
	 public  void buildHierarchyTree(GenerateHierarchy localRoot, Map<Integer, GenerateHierarchy> employees) {
		 GenerateHierarchy employee = localRoot;
		List<GenerateHierarchy> subordinates = getSubordinatesById(employee.getId(),employees);
		employee.setSubordinates(subordinates);
        if (subordinates.size() == 0) {

            service.save(employee);
            return;
        }

        
        for (GenerateHierarchy e : subordinates) {
        	buildHierarchyTree(e,employees);
        }
        service.save(employee);
	 }



	  
	  @RequestMapping("/GenerateHierarchy") public String
	  showNewGenerateHierarchyPage(Model model) { 
	  GenerateHierarchy GenerateHierarchy  = new GenerateHierarchy(0, "", null, null, null, 0, null,0);
	  model.addAttribute("GenerateHierarchy", GenerateHierarchy);
	  List<GenerateHierarchy> GenerateHierarchylist = service.listAll();
	  model.addAttribute("GenerateHierarchylist", GenerateHierarchylist);
	  
	  return "Generate_Employee_Hierarchy";
	  
	  
	  }
	 
		@RequestMapping(value = "/saveGenerateHierarchy", method = RequestMethod.POST)
		public String saveEmployee(@ModelAttribute("GenerateHierarchy") GenerateHierarchy values) {
			
		GenerateHierarchy employee = new GenerateHierarchy(values.getId(),values.getFirst_name(),values.getLast_name(), values.getDesignation(), values.getResponsibility(), values.getreportToId(),values.getSubordinates(),values.getisRoot()) ;
		
		Map<Integer, GenerateHierarchy> employees =  new HashMap<Integer, GenerateHierarchy>();
			
			if(employee.getFirst_name().contentEquals("faisal")) 
			{
				root = employee;
				employees.put(employee.getId(), employee);
				buildHierarchyTree(root,employees);
				
			}
			else {
				List<GenerateHierarchy> EmpHierarchy = service.listAll();
				root = EmpHierarchy.get(0);
				int size_of_nodes = EmpHierarchy.size();
				for (int i=0; i< size_of_nodes;i++)
				{
					EmpHierarchy.get(i).setSubordinates(null);
					employees.put(EmpHierarchy.get(i).getId(), EmpHierarchy.get(i));
					
				}
				employees.put(employee.getId(), employee);
				
				buildHierarchyTree(root,employees);
			}
			return "redirect:/Display";
		}



}