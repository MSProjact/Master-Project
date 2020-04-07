package BIA.Business.Impact.Analysis.Controller;

import BIA.Business.Impact.Analysis.Model.Employees;
import BIA.Business.Impact.Analysis.Model.Role;
import BIA.Business.Impact.Analysis.Service.EmployeesService;
import BIA.Business.Impact.Analysis.Validator.RoleValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

/**
 * @date 2020/02/26 16:30:10
 * 
 *       Basically this controller is useed for handling the all the
 *       functionalities related employee.
 */
@Controller("/")
public class EmployeesController {

	@Autowired
	private EmployeesService service;

	/**
	 * View home page.
	 *
	 * 
	 * @param model the model
	 * @return the string as a specific html page with model attribute.
	 * 
	 *         Here, We added the updated page name in return string.
	 */
	@RequestMapping("/manageEmployees")
	public String viewHomePage(Model model) {
		List<Employees> Employeeslist = service.listAll();
		model.addAttribute("Employeeslist", Employeeslist);
		return "Manage_Employees";
	}

	@RequestMapping("/new")
	public String showNewEmployeesPage(Model model, HttpServletRequest request) {
		RoleValidator.checkUserRights(request, Role.EMPLOYEE);
		Employees Employees = new Employees();
		model.addAttribute("Employees", Employees);
		List<BIA.Business.Impact.Analysis.Model.Employees> Employeeslist = service.listAll();
		model.addAttribute("Employeeslist", Employeeslist);
		return "Add_NewEmployee";
	}

	/**
	 * Save employee.
	 *
	 * 
	 * @param Employees the employees model class object
	 * @return the redirect string URL.
	 * 
	 *         here, We added the redirect URL with hierarchy page before it was on
	 *         index page.
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveEmployee(@ModelAttribute("Employees") Employees Employees, HttpServletRequest request) {
		service.save(Employees);
		return "redirect:/manageEmployees";
	}

	@RequestMapping("/edit/{id}")
	public String showEditEmployeePage(@PathVariable(name = "id") String id, Model model, HttpServletRequest request) {
//		ModelAndView mav = new ModelAndView("Edit_Employees");
		RoleValidator.checkUserRights(request, Role.MANAGER);
		Employees Employees = service.get(id);
		model.addAttribute("Employees", Employees);
		List<BIA.Business.Impact.Analysis.Model.Employees> Employeeslist = service.listAll();
		model.addAttribute("Employeeslist", Employeeslist);
		return "Edit_Employees";
	}

	/**
	 * Delete employees.
	 *
	 * 
	 * @param id the id
	 * @return the redirect string URL.
	 * 
	 *         here, We added the redirect URL with manageEmployees page before it
	 *         was on index page.
	 */
	@RequestMapping("/delete/{id}")
	public String deleteEmployees(@PathVariable(name = "id") String id, HttpServletRequest request) {
		RoleValidator.checkUserRights(request, Role.MANAGER);
		service.delete(id);
		return "redirect:/manageEmployees";
	}
	
	
	/**
	 * Employees.
	 * 
	 * @param model the model
	 * @return it return the Employees page with model object.
	 * 
	 *         Here, We created new list in which first I have added the parent list
	 *         and called the getSubModul method for getting the child list for
	 *         specific parent.
	 */
	@RequestMapping("/viewEmployees")
	public String generateHierarchy(HttpServletRequest request, Model model) {
		List<Employees> generateHierarchy = service.listAll();
		List<Employees> mainHierarchyList = new ArrayList<Employees>();
		HttpSession session = request.getSession();
		Employees me = (Employees) session.getAttribute(LoginController.SESSION_ME);
		for (final Employees parentHierarchy : generateHierarchy) {
			if (parentHierarchy.getId().equals(me.getId())) {
				// if parentHierarchy is mine, add my sub-module
				List<Employees> childHierarchyList = getSubModule(parentHierarchy.getId(),
						generateHierarchy);
				parentHierarchy.setSubEmployees(childHierarchyList);
				mainHierarchyList.add(parentHierarchy);
			}
		}
		model.addAttribute("EmployeesList", mainHierarchyList);
		return "Employee";
	}
	
	/**
	 * Gets the sub module.
	 *
	 * @param i           it takes the parent id into param for comparing with
	 *                     child report to id.
	 * @param employeeList in this list, all the employee list are available for
	 *                     comparing the parent id with child report to id.
	 * @return the employee model list who comes under the particular parent.
	 * 
	 *         here, We called this method recursively for getting the tree
	 *         hierarchy
	 */
	public List<Employees> getSubModule(String i, List<Employees> employees) {
		List<Employees> subHierarchyList = new ArrayList<Employees>();
		for (final Employees child : employees) {
			if (child.getReportToid().equals(i)) {
				subHierarchyList.add(child);
			}
		}
		if (subHierarchyList.size() > 0) {
			for (final Employees subHierarchy : subHierarchyList) {
				List<Employees> childHierarchyList = getSubModule(subHierarchy.getId(), employees);
				subHierarchy.setSubEmployees(childHierarchyList);
			}
		}
		return subHierarchyList;
	}
}
