package BIA.Business.Impact.Analysis.Controller;

import BIA.Business.Impact.Analysis.Model.Departments;
import BIA.Business.Impact.Analysis.Model.Employees;
import BIA.Business.Impact.Analysis.Model.ProductionSteps;
import BIA.Business.Impact.Analysis.Model.Role;
import BIA.Business.Impact.Analysis.Service.EmployeesService;
import BIA.Business.Impact.Analysis.Service.ProductionStepsService;
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

import java.util.ArrayList;
import java.util.List;

@Controller("/ProductionSteps")
public class ProductionStepsController {

	@Autowired
	private ProductionStepsService service;
	
	@Autowired
	private EmployeesService service1;

	@RequestMapping("/ProductionStepslist")
	public String viewHomePage(Model model) {
		List<ProductionSteps> ProductionStepslist = service.listAll();
		model.addAttribute("ProductionStepslist", ProductionStepslist);
		return "Manage_ProductionSteps";
	}

	@RequestMapping("/newProductionStep")
	public String showNewProductionStepsPage(Model model, HttpServletRequest request) {
		RoleValidator.checkUserRights(request, Role.EMPLOYEE);
		ProductionSteps ProductionSteps = new ProductionSteps();
		model.addAttribute("ProductionSteps", ProductionSteps);
		
		List<Employees> employees = service1.listAll();
		model.addAttribute("Employeeslist", employees);
		
		return "Add_NewProductionSteps";
	}

	/*
	 * @RequestMapping(value = "/saveProductionStep", method = RequestMethod.POST)
	 * public String saveProduct(@ModelAttribute("ProductionSteps") ProductionSteps
	 * ProductionSteps) { service.save(ProductionSteps); return
	 * "redirect:/ProductionStepslist"; }
	 */
	
	@RequestMapping(value = "/saveProductionStep", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute("ProductionSteps") ProductionSteps ProductionSteps, HttpServletRequest request) {
		service.save(ProductionSteps);
		return "redirect:/newProductionStep";
	}
	

	@RequestMapping("/editProductionStep/{id}")
	public ModelAndView showEditProductPage(@PathVariable(name = "id") String id, HttpServletRequest request) {
		RoleValidator.checkUserRights(request, Role.MANAGER);
		ModelAndView mav = new ModelAndView("Edit_ProductionSteps");
		
		ProductionSteps ProductionSteps = service.get(id);
		mav.addObject("ProductionSteps", ProductionSteps);
		
		
		return mav;
	}

	@RequestMapping("/deleteProductionStep/{id}")
	public String deleteProductionSteps(@PathVariable(name = "id") String id, HttpServletRequest request) {
		RoleValidator.checkUserRights(request, Role.MANAGER);
		service.delete(id);
		return "redirect:/ProductionStepslist";
	}
	
	/**
	 * Production steps.
	 * 
	 * @param model the model
	 * @return it return the ProductionSteps page with model object.
	 * 
	 */
	@RequestMapping("/viewProductSteps")
	public String viewProductSteps(HttpServletRequest request, Model model) {
		List<ProductionSteps> productionSteps = service.listAll();
		model.addAttribute("ProductionStepsList", productionSteps);
		return "ProductionSteps";
	}
}
