package BIA.Business.Impact.Analysis.Controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import BIA.Business.Impact.Analysis.Model.Employees;
import BIA.Business.Impact.Analysis.Service.EmployeesService;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
/**
 * @author Prabjyot Saini 
 * @date 2020/02/26 16:30:10
 * 
 * Basically this controller is useed for handling the all the functionalities related employee.
 */
@Controller("/")
public class EmployeesController {

	@Autowired
	private EmployeesService service;

	/**
	 * View home page.
	 *
	 * @author Prabjyot Saini 
	 * @date 2020/02/26 16:30:10
	 * @param model the model
	 * @return the string as a specific html page with model attribute.
	 * 
	 * Here, I have added the updated page name in return string.
	 */
	@RequestMapping("/manageEmployees")
	public String viewHomePage(Model model) {
		List<Employees> Employeeslist = service.listAll();
		model.addAttribute("Employeeslist", Employeeslist);
		return "Manage_Employees";
	}
	
	@RequestMapping("/new")
	public String showNewEmployeesPage(Model model) {
		Employees Employees = new Employees();
		model.addAttribute("Employees", Employees);
		List<Employees> Employeeslist = service.listAll();
		model.addAttribute("Employeeslist", Employeeslist);
		return "Add_NewEmployee";
	}

	/**
	 * Save employee.
	 *
	 * @author Prabjyot Saini 
	 * @date 2020/02/26 16:30:10
	 * @param Employees the employees model class object
	 * @return the redirect string URL.
	 * 
	 * here, I have added the redirect URL with hierarchy page before it was on index page.
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveEmployee(@ModelAttribute("Employees") Employees Employees) {
		service.save(Employees);
		return "redirect:/viewHierarchy";
	}

	@RequestMapping("/edit/{id}") 
	public String showEditEmployeePage(@PathVariable(name = "id") int id, Model model) {
//		ModelAndView mav = new ModelAndView("Edit_Employees"); 
		Employees Employees = service.get(id);
		model.addAttribute("Employees", Employees);
		List<Employees> Employeeslist = service.listAll();
		model.addAttribute("Employeeslist", Employeeslist);
		return "Edit_Employees"; 
	}
	 
	/**
	 * Delete employees.
	 *
	 * @author Prabjyot Saini 
	 * @date 2020/02/26 16:30:10
	 * @param id the id
	 * @return the redirect string URL.
	 * 
	 * here, I have added the redirect URL with manageEmployees page before it was on index page.
	 */
	@RequestMapping("/delete/{id}")
	public String deleteEmployees(@PathVariable(name = "id") int id) {
		service.delete(id);
		return "redirect:/manageEmployees";       
	}

	/**
	 * Generate hierarchy.
	 * 
	 * @author Prabjyot Saini 
	 * @date 2020/02/26 16:30:10
	 * @param model the model
	 * @return it return the EmployeeHierarchy page with model object.
	 * 
	 * Here, I have created new list in which first I have added the parent list and called the getSubModul method for getting the child list for specific parent. 
	 */
	@RequestMapping("/viewHierarchy")
	public String generateHierarchy(Model model) {
		List<Employees> employeeList = service.listAll();
		List<Employees> mainHierarchyList = new ArrayList<Employees>();

		for (final Employees parentEmp : employeeList) {
			if (parentEmp.getReportToid() == 0) {
				List<Employees> childHierarchyList = getSubModule(parentEmp.getId(), employeeList);
				parentEmp.setSubEmployees(childHierarchyList);
				mainHierarchyList.add(parentEmp);
			}
		}
		model.addAttribute("Employeelist", mainHierarchyList);
		return "EmployeeHierarchy";
	}

	/**
	 * Gets the sub module.
	 *
	 * @author Prabjyot Saini 
	 * @date 2020/02/26 16:30:10
	 * @param id it takes the parent id into param for comparing with child report to id.
	 * @param employeeList in this list, all the employee list are available for comparing the parent id with child report to id.
	 * @return the employee model list who comes under the particular parent.
	 * 
	 * here, I have called this methods recursively for getting the tree hierarchy
	 */
	// For getting the sub employee list
	public List<Employees> getSubModule(int id, List<Employees> employeeList) {
		List<Employees> subEmployeeList = new ArrayList<Employees>();
		for (final Employees childEmp : employeeList) {
			if (childEmp.getReportToid() == id) {
				subEmployeeList.add(childEmp);
			}
		}
		if (subEmployeeList.size() > 0) {
			for (final Employees subHierarchy : subEmployeeList) {
				List<Employees> childEmpList = getSubModule(subHierarchy.getId(), employeeList);
				subHierarchy.setSubEmployees(childEmpList);
			}
		}
		return subEmployeeList;
	}
}
