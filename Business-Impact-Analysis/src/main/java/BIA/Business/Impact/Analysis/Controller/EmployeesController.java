package BIA.Business.Impact.Analysis.Controller;

import BIA.Business.Impact.Analysis.Model.Employees;
import BIA.Business.Impact.Analysis.Model.Role;
import BIA.Business.Impact.Analysis.Service.EmployeesService;
import BIA.Business.Impact.Analysis.utils.UserUtil;

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
import java.util.Collections;
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
		UserUtil.checkUserRights(request, Role.EMPLOYEE);
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
	public String saveEmployee(@ModelAttribute("Employees") Employees employee, HttpServletRequest request) {
		service.save(employee);
		return "redirect:/manageEmployees";
	}

	@RequestMapping("/edit/{id}")
	public String showEditEmployeePage(@PathVariable(name = "id") String id, Model model, HttpServletRequest request) {
//		ModelAndView mav = new ModelAndView("Edit_Employees");
		UserUtil.checkUserRights(request, Role.MANAGER);
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
		UserUtil.checkUserRights(request, Role.MANAGER);
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
		model.addAttribute("EmployeesList", service.getSubEmployeesForCurrentUser());
		return "Employee";
	}
}
