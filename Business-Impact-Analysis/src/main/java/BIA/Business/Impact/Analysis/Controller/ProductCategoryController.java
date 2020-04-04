package BIA.Business.Impact.Analysis.Controller;

import BIA.Business.Impact.Analysis.Model.ProductCategory;
import BIA.Business.Impact.Analysis.Model.Role;
import BIA.Business.Impact.Analysis.Service.ProductCategoryService;
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

@Controller("/ProductCategory")
public class ProductCategoryController {

	@Autowired
	private ProductCategoryService service;

	@RequestMapping("/ProductsCategorylist")
	public String viewHomePage(Model model) {
		List<ProductCategory> ProductCategorylist = service.listAll();
		model.addAttribute("ProductCategorylist", ProductCategorylist);

		return "Manage_ProductCategory";
	}

	@RequestMapping("/newProductCategory")
	public String showNewProductsPage(Model model, HttpServletRequest request) {
		RoleValidator.checkUserRights(request, Role.EMPLOYEE);
		ProductCategory ProductCategory = new ProductCategory();
		model.addAttribute("ProductCategory", ProductCategory);

		return "Add_NewProductCategory";
	}

	
	  @RequestMapping(value = "/saveProductCategory", method = RequestMethod.POST) public
	  String saveProduct(@ModelAttribute("ProductCategory") ProductCategory ProductCategory, HttpServletRequest request) {
		  service.save(ProductCategory); return "redirect:/newProductionStep";
	}
	  
	  
	  
	
	  @RequestMapping("/editProductCategory/{id}") public ModelAndView showEditProductPage(@PathVariable(name = "id") String id,
																						   HttpServletRequest request) {
		  RoleValidator.checkUserRights(request, Role.MANAGER);
		ModelAndView mav = new ModelAndView("Edit_ProductCategory");
		ProductCategory ProductCategory = service.get(id);
	  mav.addObject("ProductCategory", ProductCategory); return mav; }
	  
	  
	  @RequestMapping("/deleteProductCategory/{id}") public String
	  deleteProducts(@PathVariable(name = "id") String id, HttpServletRequest request) {
		  RoleValidator.checkUserRights(request, Role.MANAGER);
		service.delete(id);
	  return "redirect:/ProductsCategorylist"; }
	 
	 
}
