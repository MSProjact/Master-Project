package BIA.Business.Impact.Analysis.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import BIA.Business.Impact.Analysis.Model.GenerateHierarchy;
import BIA.Business.Impact.Analysis.Service.GenerateHierarchyService;
/**
 * 
 * 
 * Before this controller was used to Display the page for showing hierarchy now it is not in use.
 */
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
		List<GenerateHierarchy> mainModule = new ArrayList<GenerateHierarchy>();

		for (GenerateHierarchy parent : EmpHierarchy) {
			if (parent.getReportToId() == 0) {
				List<GenerateHierarchy> childModelList = getSubModule(parent.getId(), EmpHierarchy);
				parent.setSubordinates(childModelList);
				mainModule.add(parent);
			}
		}
		model.addAttribute("Employeelist", mainModule);
		return "indexGenerateHierarchy.html";
	}

	// For getting the subModule list
	public List<GenerateHierarchy> getSubModule(int id, List<GenerateHierarchy> EmpHierarchy) {
		List<GenerateHierarchy> subModule = new ArrayList<GenerateHierarchy>();
		for (final GenerateHierarchy empHierarchy : EmpHierarchy) {
			if (empHierarchy.getReportToId() == id) {
				subModule.add(empHierarchy);
			}
		}
		if (subModule.size() > 0) {
			for (final GenerateHierarchy subHierarchy : subModule) {
				List<GenerateHierarchy> childModelList = getSubModule(subHierarchy.getId(), EmpHierarchy);
				subHierarchy.setSubordinates(childModelList);
			}
		}
		return subModule;
	}

	
// Bellow two methods are not in use.	
	
//	private static List<GenerateHierarchy> getSubordinatesById(int empId, Map<Integer, GenerateHierarchy> employees) {
//		List<GenerateHierarchy> subordinates = new ArrayList<GenerateHierarchy>();
//		for (GenerateHierarchy e : employees.values()) {
//			if (e.getReportToId() == empId) {
//				subordinates.add(e);
//			}
//		}
//		return subordinates;
//	}
//
//	// build tree recursively
//	public void buildHierarchyTree(GenerateHierarchy localRoot, Map<Integer, GenerateHierarchy> employees) {
//		GenerateHierarchy employee = localRoot;
//		List<GenerateHierarchy> subordinates = getSubordinatesById(employee.getId(), employees);
//		employee.setSubordinates(subordinates);
//		if (subordinates.size() == 0) {
//			service.save(employee);
//			return;
//		}
//		for (GenerateHierarchy e : subordinates) {
//			buildHierarchyTree(e, employees);
//		}
//		service.save(employee);
//	}

	
//  Now This page(Generate_Employee_Hierarchy) was not need as confirmed with Yasir.	
	
//	@RequestMapping("/GenerateHierarchy")
//	public String showNewGenerateHierarchyPage(Model model) {
//		GenerateHierarchy GenerateHierarchy = new GenerateHierarchy(0, "", 0, null/* ,0 */);
//		model.addAttribute("GenerateHierarchy", GenerateHierarchy);
//		List<GenerateHierarchy> GenerateHierarchylist = service.listAll();
//		model.addAttribute("GenerateHierarchylist", GenerateHierarchylist);
//		return "Generate_Employee_Hierarchy";
//	}


//  This controller also used for the storing the data for the Generate_Employee_Hierarchy page.
	
//	@RequestMapping(value = "/saveGenerateHierarchy", method = RequestMethod.POST)
//	public String saveEmployee(@ModelAttribute("GenerateHierarchy") GenerateHierarchy values) {
//
//		GenerateHierarchy employee = new GenerateHierarchy(values.getId(), values.getName(), values.getReportToId(), values.getSubordinates()/* ,values.getisRoot() */);
//
//		Map<Integer, GenerateHierarchy> employees = new HashMap<Integer, GenerateHierarchy>();
//
//		if (employee.getName().contentEquals("faisal")) {
//			root = employee;
//			employees.put(employee.getId(), employee);
//			buildHierarchyTree(root, employees);
//		} else {
//			List<GenerateHierarchy> EmpHierarchy = service.listAll();
//			root = EmpHierarchy.get(0);
//			int size_of_nodes = EmpHierarchy.size();
//			for (int i = 0; i < size_of_nodes; i++) {
//				EmpHierarchy.get(i).setSubordinates(null);
//				employees.put(EmpHierarchy.get(i).getId(), EmpHierarchy.get(i));
//			}
//			employees.put(employee.getId(), employee);
//			buildHierarchyTree(root, employees);
//		}
//		return "redirect:/Display";
//	}

}
