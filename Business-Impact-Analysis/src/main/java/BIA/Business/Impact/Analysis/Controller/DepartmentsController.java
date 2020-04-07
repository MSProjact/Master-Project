package BIA.Business.Impact.Analysis.Controller;

import BIA.Business.Impact.Analysis.Model.Departments;
import BIA.Business.Impact.Analysis.Model.Employees;
import BIA.Business.Impact.Analysis.Model.Role;
import BIA.Business.Impact.Analysis.Service.DepartmentsService;
import BIA.Business.Impact.Analysis.Service.EmployeesService;
import BIA.Business.Impact.Analysis.Validator.RoleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller("/departments")
public class DepartmentsController {

	@Autowired
	private DepartmentsService service;
	@Autowired
	private EmployeesService service1;

	@RequestMapping("/Departmentslist")
	public String viewHomePage(Model model) {
		List<Departments> Departmentslist = service.listAll();
		model.addAttribute("Departmentslist", Departmentslist);
		return "Manage_Departments";
	}

	@RequestMapping("/newDepartment")
	public String showNewDepartmentsPage(Model model, HttpServletRequest request) {
		RoleValidator.checkUserRights(request, Role.EMPLOYEE);
		Departments Departments = new Departments();
		model.addAttribute("Departments", Departments);
		List<Employees> employees = service1.listAll();
		model.addAttribute("Employeeslist", employees);
		return "Add_NewDepartment";
	}

	@RequestMapping(value = "/saveDepartment", method = RequestMethod.POST)
	public String saveDepartment(@ModelAttribute("Departments") Departments Departments, HttpServletRequest request) {
		service.save(Departments);
		return "redirect:/Departmentslist";
	}

	@RequestMapping("/editDepartment/{id}")
	public ModelAndView showEditDepartmentPage(@PathVariable(name = "id") String id, HttpServletRequest request) {
		RoleValidator.checkUserRights(request, Role.MANAGER);
		ModelAndView mav = new ModelAndView("Edit_Departments");
		Departments Departments = service.get(id);
		mav.addObject("Departments", Departments);
		return mav;
	}

	@RequestMapping("/deleteDepartment/{id}")
	public String deleteDepartments(@PathVariable(name = "id") String id, HttpServletRequest request) {
		RoleValidator.checkUserRights(request, Role.MANAGER);
		service.delete(id);
		return "redirect:/Departmentslist";
	}
}
