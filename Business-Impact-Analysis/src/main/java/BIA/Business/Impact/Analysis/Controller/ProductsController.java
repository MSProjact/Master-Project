package BIA.Business.Impact.Analysis.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import BIA.Business.Impact.Analysis.Model.Employees;
import BIA.Business.Impact.Analysis.Model.ProductCategory;
import BIA.Business.Impact.Analysis.Model.ProductionSteps;
import BIA.Business.Impact.Analysis.Model.Products;
import BIA.Business.Impact.Analysis.Service.ProductCategoryService;
import BIA.Business.Impact.Analysis.Service.ProductionStepsService;
import BIA.Business.Impact.Analysis.Service.ProductsService;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

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
	public String showNewProductsPage(Model model) {
		Products Products = new Products();
		model.addAttribute("Products", Products);
		
		List<ProductionSteps> Functions = service1.listAll();
		model.addAttribute("ProductionStepslist", Functions);
		
		List<ProductCategory> ProductCategory = service2.listAll();
		model.addAttribute("ProductCategorylist", ProductCategory);
		
		return "Add_NewProduct";
	}

	
	
	
	@RequestMapping(value = "/saveProduct", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute("Products") Products Products) {
		service.save(Products);
		return "redirect:/Productslist";
	}

	@RequestMapping("/editproduct/{id}")
	public ModelAndView showEditProductPage(@PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView("Edit_Products");
		Products Products = service.get(id);
		mav.addObject("Products", Products);
		return mav;
	}

	@RequestMapping("/deleteProduct/{id}")
	public String deleteProducts(@PathVariable(name = "id") int id) {
		service.delete(id);
		return "redirect:/Productslist";
	}
}
