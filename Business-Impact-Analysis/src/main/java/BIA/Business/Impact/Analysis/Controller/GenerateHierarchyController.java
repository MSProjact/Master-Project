package BIA.Business.Impact.Analysis.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import BIA.Business.Impact.Analysis.Model.Departments;
import BIA.Business.Impact.Analysis.Model.Employees;
import BIA.Business.Impact.Analysis.Model.GenerateHierarchy;
import BIA.Business.Impact.Analysis.Model.ProductCategory;
import BIA.Business.Impact.Analysis.Model.ProductionSteps;
import BIA.Business.Impact.Analysis.Model.Products;
import BIA.Business.Impact.Analysis.Service.DepartmentsService;
import BIA.Business.Impact.Analysis.Service.EmployeesService;
import BIA.Business.Impact.Analysis.Service.GenerateHierarchyService;
import BIA.Business.Impact.Analysis.Service.ProductCategoryService;
import BIA.Business.Impact.Analysis.Service.ProductionStepsService;
import BIA.Business.Impact.Analysis.Service.ProductsService;

/**
 * 
 * 
 *       Basically this controller is used for handling the all the
 *       functionalities related employee.
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
	public String generateHierarchyPage (Model model) {
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
	public String manageHierarchy(Model model) {
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
	public String deleteHierarchy(@PathVariable(name = "id") int id) {
		generateHierarchyService.delete(id);
		return "redirect:/manageHierarchy";
	}

	/**
	 * Edits the hierarchy.
	 *
	 * @param id the id
	 * @param model the model
	 * @return the model string with html page name
	 */
	@RequestMapping("/editHierarchy/{id}")
	public String editHierarchy(@PathVariable(name = "id") int id, Model model) {
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
	 *         Here, We created new list in which first we have added the parent list
	 *         and called the getSubModul method for getting the child list for
	 *         specific parent.
	 */
	@RequestMapping("/viewHierarchy")
	public String generateHierarchy(Model model) {
		List<GenerateHierarchy> generateHierarchy = generateHierarchyService.listAll();
		List<GenerateHierarchy> mainHierarchyList = new ArrayList<GenerateHierarchy>();

		for (final GenerateHierarchy parentHierarchy : generateHierarchy) {
			if (parentHierarchy.getReportToEmployeeId() == 0) {
				List<GenerateHierarchy> childHierarchyList = getSubModule(parentHierarchy.getEmployeeId(), generateHierarchy);
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
	 * @param id           it takes the parent id into param for comparing with
	 *                     child report to id.
	 * @param employeeList in this list, all the employee list are available for
	 *                     comparing the parent id with child report to id.
	 * @return the employee model list who comes under the particular parent.
	 * 
	 *         here, We called this method recursively for getting the tree
	 *         hierarchy
	 */
	public List<GenerateHierarchy> getSubModule(int id, List<GenerateHierarchy> generateHierarchy) {
		List<GenerateHierarchy> subHierarchyList = new ArrayList<GenerateHierarchy>();
		for (final GenerateHierarchy child : generateHierarchy) {
			if (child.getReportToEmployeeId() == id) {
				subHierarchyList.add(child);
			}
		}
		if (subHierarchyList.size() > 0) {
			for (final GenerateHierarchy subHierarchy : subHierarchyList) {
				List<GenerateHierarchy> childHierarchyList = getSubModule(subHierarchy.getEmployeeId(), generateHierarchy);
				subHierarchy.setSubGenerateHierarchy(childHierarchyList);
			}
		}
		return subHierarchyList;
	}
}