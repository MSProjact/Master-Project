package BIA.Business.Impact.Analysis.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import BIA.Business.Impact.Analysis.Model.Employees;
import BIA.Business.Impact.Analysis.Service.EmployeesService;

/**
 * 
 * 
 *       Basically this controller is used for handling the all the
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
	 * 
	 * @param Employees the employees model class object
	 * @return the redirect string URL.
	 * 
	 *         here, We added the redirect URL with hierarchy page before it was on
	 *         index page.
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveEmployee(@ModelAttribute("Employees") Employees Employees) {
		service.save(Employees);
		return "redirect:/manageEmployees";
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
	 * 
	 * @param id the id
	 * @return the redirect string URL.
	 * 
	 *         here, We added the redirect URL with manageEmployees page before it
	 *         was on index page.
	 */
	@RequestMapping("/delete/{id}")
	public String deleteEmployees(@PathVariable(name = "id") int id) {
		service.delete(id);
		return "redirect:/manageEmployees";
	}
}
