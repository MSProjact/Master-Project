package BIA.Business.Impact.Analysis.Controller;

import BIA.Business.Impact.Analysis.Model.*;
import BIA.Business.Impact.Analysis.Service.*;
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
 * 
 * 
 * Basically this controller is used for handling the all the functionalities
 * related generate hierarchy.
 */
@Controller("/GenerateHierarchyController")
public class GenerateHierarchyController {

	@Autowired
	private EmployeesService employeeService;
	@Autowired
	private ProductsService productService;
	@Autowired
	private ProductionStepsService productionService;
	@Autowired
	private ProductCategoryService categoryService;
	@Autowired
	private DepartmentsService departmentService;
	@Autowired
	private GenerateHierarchyService generateHierarchyService;

	/**
	 * Generate hierarchy page.
	 *
	 * @param model the model
	 * @return the model string with html page name
	 */
	@RequestMapping("/generateHierarchy")
	public String generateHierarchyPage(Model model, HttpServletRequest request) {
		RoleValidator.checkUserRights(request, Role.MANAGER);
		GenerateHierarchy generateHierarchy = new GenerateHierarchy();
		model.addAttribute("GenerateHierarchy", generateHierarchy);
		List<Employees> Employeeslist = employeeService.listAll();
		model.addAttribute("Employeeslist", Employeeslist);
		List<Products> Productslist = productService.listAll();
		model.addAttribute("Productslist", Productslist);
		List<ProductionSteps> ProductionStepslist = productionService.listAll();
		model.addAttribute("ProductionStepslist", ProductionStepslist);
		List<ProductCategory> ProductCategorylist = categoryService.listAll();
		model.addAttribute("ProductCategorylist", ProductCategorylist);
		List<Departments> Departmentslist = departmentService.listAll();
		model.addAttribute("Departmentslist", Departmentslist);
		return "GenerateHierarchy";
	}

	/**
	 * Save employee.
	 *
	 * @param GenerateHierarchy the generate hierarchy
	 * @return the redirect string on specific method.
	 */
	@RequestMapping(value = "/saveHierarchy", method = RequestMethod.POST)
	public String saveEmployee(@ModelAttribute("GenerateHierarchy") GenerateHierarchy GenerateHierarchy) {
		generateHierarchyService.save(GenerateHierarchy);
		return "redirect:/manageHierarchy";
	}

	/**
	 * Manage hierarchy.
	 *
	 * @param model the model
	 * @return the model string with html page name
	 */
	@RequestMapping("/manageHierarchy")
	public String manageHierarchy(Model model, HttpServletRequest request) {
		RoleValidator.checkUserRights(request, Role.MANAGER);
		List<GenerateHierarchy> hierarchyList = generateHierarchyService.listAll();
		model.addAttribute("hierarchyList", hierarchyList);
		return "Manage_Hierarchy";
	}

	/**
	 * Delete hierarchy.
	 *
	 * @param id the id
	 * @return the redirect string on specific method.
	 */
	@RequestMapping("/deleteHierarchy/{id}")
	public String deleteHierarchy(@PathVariable(name = "id") String id, HttpServletRequest request) {
		RoleValidator.checkUserRights(request, Role.MANAGER);
		generateHierarchyService.delete(id);
		return "redirect:/manageHierarchy";
	}

	/**
	 * Edits the hierarchy.
	 *
	 * @param id    the id
	 * @param model the model
	 * @return the model string with html page name
	 */
	@RequestMapping("/editHierarchy/{id}")
	public String editHierarchy(@PathVariable(name = "id") String id, Model model, HttpServletRequest request) {
		RoleValidator.checkUserRights(request, Role.MANAGER);
		GenerateHierarchy generateHierarchy = generateHierarchyService.get(id);
		model.addAttribute("GenerateHierarchy", generateHierarchy);
		List<Employees> Employeeslist = employeeService.listAll();
		model.addAttribute("Employeeslist", Employeeslist);
		List<Products> Productslist = productService.listAll();
		model.addAttribute("Productslist", Productslist);
		List<ProductionSteps> ProductionStepslist = productionService.listAll();
		model.addAttribute("ProductionStepslist", ProductionStepslist);
		List<ProductCategory> ProductCategorylist = categoryService.listAll();
		model.addAttribute("ProductCategorylist", ProductCategorylist);
		List<Departments> Departmentslist = departmentService.listAll();
		model.addAttribute("Departmentslist", Departmentslist);
		return "Edit_Hierarchy";
	}

	/**
	 * Generate hierarchy.
	 * 
	 * @param model the model
	 * @return it return the EmployeeHierarchy page with model object.
	 * 
	 *         Here, We created new list in which first I have added the parent list
	 *         and called the getSubModul method for getting the child list for
	 *         specific parent.
	 */
	@RequestMapping("/viewHierarchy")
	public String generateHierarchy(HttpServletRequest request, Model model) {
		List<GenerateHierarchy> generateHierarchy = generateHierarchyService.listAll();
		List<GenerateHierarchy> mainHierarchyList = new ArrayList<GenerateHierarchy>();
		HttpSession session = request.getSession();
		Employees me = (Employees) session.getAttribute(LoginController.SESSION_ME);
		for (final GenerateHierarchy parentHierarchy : generateHierarchy) {
			if (parentHierarchy.getEmployeeId().equals(me.getId())) {
				// if parentHierarchy is mine, add my sub-module
				List<GenerateHierarchy> childHierarchyList = getSubModule(parentHierarchy.getEmployeeId(),
						generateHierarchy);
				parentHierarchy.setSubGenerateHierarchy(childHierarchyList);
				mainHierarchyList.add(parentHierarchy);
			}
		}
		model.addAttribute("HierarchyList", mainHierarchyList);
		return "Hierarchy";
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
	public List<GenerateHierarchy> getSubModule(String i, List<GenerateHierarchy> generateHierarchy) {
		List<GenerateHierarchy> subHierarchyList = new ArrayList<GenerateHierarchy>();
		for (final GenerateHierarchy child : generateHierarchy) {
			if (child.getReportToEmployeeId().equals(i)) {
				subHierarchyList.add(child);
			}
		}
		if (subHierarchyList.size() > 0) {
			for (final GenerateHierarchy subHierarchy : subHierarchyList) {
				List<GenerateHierarchy> childHierarchyList = getSubModule(subHierarchy.getEmployeeId(),
						generateHierarchy);
				subHierarchy.setSubGenerateHierarchy(childHierarchyList);
			}
		}
		return subHierarchyList;
	}
}
