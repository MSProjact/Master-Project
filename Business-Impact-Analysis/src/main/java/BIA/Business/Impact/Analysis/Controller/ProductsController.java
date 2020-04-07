package BIA.Business.Impact.Analysis.Controller;

import BIA.Business.Impact.Analysis.Model.*;
import BIA.Business.Impact.Analysis.Service.ProductCategoryService;
import BIA.Business.Impact.Analysis.Service.ProductionStepsService;
import BIA.Business.Impact.Analysis.Service.ProductsService;
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

@Controller("/products")
public class ProductsController {

	@Autowired
	private ProductsService service;
	
	@Autowired
	private ProductionStepsService service1;
	
	@Autowired
	private ProductCategoryService service2;

	@RequestMapping("/Productslist")
	public String viewHomePage(Model model) {
		List<Products> Productslist = service.listAll();
		model.addAttribute("Productslist", Productslist);
		
		return "Manage_Products";
	}

	@RequestMapping("/newProduct")
	public String showNewProductsPage(Model model, HttpServletRequest request) {
		RoleValidator.checkUserRights(request, Role.EMPLOYEE);
		Products Products = new Products();
		model.addAttribute("Products", Products);
		
		List<ProductionSteps> Functions = service1.listAll();
		model.addAttribute("ProductionStepslist", Functions);
		
		List<ProductCategory> ProductCategory = service2.listAll();
		model.addAttribute("ProductCategorylist", ProductCategory);
		
		return "Add_NewProduct";
	}

	@RequestMapping(value = "/saveProduct", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute("Products") Products Products, HttpServletRequest request) {
		service.save(Products);
		return "redirect:/newProductCategory";
	}
	
	
	/*
	 * @RequestMapping(value = "/saveProduct", method = RequestMethod.POST) public
	 * String saveProduct(@ModelAttribute("Products") Products Products) {
	 * service.save(Products); return "redirect:/Productslist"; }
	 */

	@RequestMapping("/editproduct/{id}")
	public ModelAndView showEditProductPage(@PathVariable(name = "id") String id, HttpServletRequest request) {
		RoleValidator.checkUserRights(request, Role.MANAGER);
		ModelAndView mav = new ModelAndView("Edit_Products");
		Products Products = service.get(id);
		mav.addObject("Products", Products);
		return mav;
	}

	@RequestMapping("/deleteProduct/{id}")
	public String deleteProducts(@PathVariable(name = "id") String id, HttpServletRequest request) {
		RoleValidator.checkUserRights(request, Role.MANAGER);
		service.delete(id);
		return "redirect:/Productslist";
	}
}
