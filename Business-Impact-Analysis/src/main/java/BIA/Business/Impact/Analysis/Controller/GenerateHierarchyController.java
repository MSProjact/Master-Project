package BIA.Business.Impact.Analysis.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import BIA.Business.Impact.Analysis.Model.Employees;
import BIA.Business.Impact.Analysis.Model.GenerateHierarchy;
import BIA.Business.Impact.Analysis.Service.GenerateHierarchyService;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

@Controller("/GenerateHierarchyController")
public class GenerateHierarchyController {

	@Autowired
	private GenerateHierarchyService service;
	
	static Map<Integer, GenerateHierarchy> employees;
	static GenerateHierarchy root;
	public int level;

	@RequestMapping("/Display")
	
	public String initTree(Model model) {
			
		employees = new HashMap<Integer, GenerateHierarchy>();
		
	    List<GenerateHierarchy> EmpHierarchy = service.listAll();
			model.addAttribute("Employeelist", EmpHierarchy);
			
			return "indexGenerateHierarchy.html";
	        
			
	 }
	

	
	 private static List<GenerateHierarchy> getSubordinatesById(int reportToId) {
		 List<GenerateHierarchy> subordinates = new ArrayList<GenerateHierarchy>();
		 for (GenerateHierarchy e : employees.values()) {
           if (e.getId() == reportToId) {
           	subordinates.add(e);
           }
		 }
		 return subordinates;
	 }
	 	 
	 //build tree recursively
	 private static void buildHierarchyTree(GenerateHierarchy localRoot) {
		 GenerateHierarchy employee = localRoot;
		List<GenerateHierarchy> subordinates = getSubordinatesById(employee.getId());
		employee.setSubordinates(subordinates);
        if (subordinates.size() == 0) {
            return;
        }

        for (GenerateHierarchy e : subordinates) {
        	buildHierarchyTree(e);
        }
	 }


	
	/*
	 * @RequestMapping("/Display") public String viewHomePage(Model model) {
	 * List<GenerateHierarchy> GenerateHierarchylist = service.listAll();
	 * model.addAttribute("GenerateHierarchylist", GenerateHierarchylist); return
	 * "indexGenerateHierarchy";
	 * 
	 * }
	 */
	  
	  @RequestMapping("/GenerateHierarchy") public String
	  showNewGenerateHierarchyPage(Model model) { GenerateHierarchy
	  GenerateHierarchy  = new GenerateHierarchy(level, null, level, null);
	  model.addAttribute("GenerateHierarchy", GenerateHierarchy);
	  List<GenerateHierarchy> GenerateHierarchylist = service.listAll();
	  model.addAttribute("GenerateHierarchylist", GenerateHierarchylist);
	  
	  return "Generate_Employee_Hierarchy";
	  
	  
	  }
	 
		@RequestMapping(value = "/saveGenerateHierarchy", method = RequestMethod.POST)
		public String saveEmployee(@ModelAttribute("GenerateHierarchy") GenerateHierarchy GenerateHierarchy) {
			service.save(GenerateHierarchy);

			return "redirect:/Display";
		}



}
