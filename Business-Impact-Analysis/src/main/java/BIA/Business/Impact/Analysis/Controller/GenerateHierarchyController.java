package BIA.Business.Impact.Analysis.Controller;

import BIA.Business.Impact.Analysis.Model.*;
import BIA.Business.Impact.Analysis.Service.*;
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
		UserUtil.checkUserRights(request, Role.MANAGER);
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
	public String saveEmployee(@ModelAttribute("GenerateHierarchy") GenerateHierarchy generateHierarchy) {
		generateHierarchyService.save(generateHierarchy);
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
		UserUtil.checkUserRights(request, Role.MANAGER);
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
		UserUtil.checkUserRights(request, Role.MANAGER);
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
		UserUtil.checkUserRights(request, Role.MANAGER);
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
		model.addAttribute("HierarchyList", generateHierarchyService.getGenerateHierarchyList());
		return "Hierarchy";
	}

}
