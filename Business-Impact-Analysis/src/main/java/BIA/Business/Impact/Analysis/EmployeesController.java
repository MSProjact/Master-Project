package BIA.Business.Impact.Analysis;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

@Controller
public class EmployeesController {

	@Autowired
	private EmployeesService service;

	@RequestMapping("/")
	public String viewHomePage(Model model) {
		List<Employees> Employeeslist = service.listAll();
		model.addAttribute("Employeeslist", Employeeslist);
		return "index";

	}

	@RequestMapping("/new")
	public String showNewEmployeesPage(Model model) {
		Employees Employees = new Employees();
		model.addAttribute("Employees", Employees);
	

		return "Add_NewEmployee";
	}
	
	
	 
	

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveEmployee(@ModelAttribute("Employees") Employees Employees) {
		service.save(Employees);

		return "redirect:/";
	}

	
	  @RequestMapping("/edit/{id}") public ModelAndView
	  showEditEmployeePage(@PathVariable(name = "id") int id) { ModelAndView mav =
	  new ModelAndView("Edit_Employees"); Employees Employees = service.get(id);
	  mav.addObject("Employees", Employees);
	  
	 return mav; }
	 
	  @RequestMapping("/delete/{id}")
	  public String deleteEmployees(@PathVariable(name = "id") int id) {
	      service.delete(id);
	      return "redirect:/";       
	  }

}
