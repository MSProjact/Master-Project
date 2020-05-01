package BIA.Business.Impact.Analysis.Controller;

import BIA.Business.Impact.Analysis.Model.*;
import BIA.Business.Impact.Analysis.Service.ProductCategoryService;
import BIA.Business.Impact.Analysis.Service.ProductionStepsService;
import BIA.Business.Impact.Analysis.Service.ProductsService;
import BIA.Business.Impact.Analysis.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller("/products")
public class ProductController {

	@Autowired
	private ProductsService service;
	
	@Autowired
	private ProductionStepsService service1;
	
	@Autowired
	private ProductCategoryService productCategoryService;

	@RequestMapping("/Productslist")
	public String viewHomePage(Model model) {
		model.addAttribute("Productslist", service.getProductListForManageProducts());
		
		return "Manage_Products";
	}

	@RequestMapping("/product/add")
	public String showNewProductsPage(Model model, HttpServletRequest request) {
		UserUtil.checkUserRights(request, Role.EMPLOYEE);
		model.addAttribute("product", new Product());
		model.addAttribute("productsList", service.listAll());
		model.addAttribute("ProductCategorylist", productCategoryService.listAll());
		return "add-product";
	}

	/*
	 * @RequestMapping(value = "/saveProduct", method = RequestMethod.POST) public
	 * String saveProduct(@ModelAttribute("Products") Products Products,
	 * HttpServletRequest request) { service.save(Products); return
	 * "redirect:/newProductCategory"; }
	 */

	  @RequestMapping(value = "/saveProduct", method = RequestMethod.POST) public
	  String saveProduct(@ModelAttribute("Products") Product product) {
	  service.save(product);
	  return "redirect:/Productslist";
	  }
	 

	@RequestMapping("/editproduct/{id}")
	public ModelAndView showEditProductPage(@PathVariable(name = "id") String id, HttpServletRequest request) {
		UserUtil.checkUserRights(request, Role.MANAGER);
		ModelAndView mav = new ModelAndView("Edit_Products");
		Product Products = service.get(id);
		mav.addObject("Products", Products);
		return mav;
	}

	@RequestMapping("/deleteProduct/{id}")
	public String deleteProducts(@PathVariable(name = "id") String id, HttpServletRequest request) {
		UserUtil.checkUserRights(request, Role.MANAGER);
		service.delete(id);
		return "redirect:/Productslist";
	}
	
	@RequestMapping("/hierarchy/product")
	public String generateHierarchy(HttpServletRequest request, Model model) {
		model.addAttribute("products", service.getProductsForCurrentEmployee());
		return "Product";
	}

	/*
	* Product analysis screen controller.
	* */
	@RequestMapping("/analysis/product")
	public String viewProductInfoHierarchy(Model model) {
		model.addAttribute("productHierarchyList", service.productAnalysisHierarchy());
	  	return "product-analysis";
	}

}
