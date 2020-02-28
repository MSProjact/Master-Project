package BIA.Business.Impact.Analysis.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import BIA.Business.Impact.Analysis.Model.ProductionSteps;
import BIA.Business.Impact.Analysis.Service.ProductionStepsService;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

@Controller("/ProductionSteps")
public class ProductionStepsController {

	@Autowired
	private ProductionStepsService service;

	@RequestMapping("/ProductionStepslist")
	public String viewHomePage(Model model) {
		List<ProductionSteps> ProductionStepslist = service.listAll();
		model.addAttribute("ProductionStepslist", ProductionStepslist);
		return "Manage_ProductionSteps";
	}

	@RequestMapping("/newProductionStep")
	public String showNewProductionStepsPage(Model model) {
		ProductionSteps ProductionSteps = new ProductionSteps();
		model.addAttribute("ProductionSteps", ProductionSteps);
		return "Add_NewProductionSteps";
	}

	@RequestMapping(value = "/saveProductionStep", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute("ProductionSteps") ProductionSteps ProductionSteps) {
		service.save(ProductionSteps);
		return "redirect:/ProductionStepslist";
	}

	@RequestMapping("/editProductionStep/{id}")
	public ModelAndView showEditProductPage(@PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView("Edit_ProductionSteps");
		ProductionSteps ProductionSteps = service.get(id);
		mav.addObject("ProductionSteps", ProductionSteps);
		return mav;
	}

	@RequestMapping("/deleteProductionStep/{id}")
	public String deleteProductionSteps(@PathVariable(name = "id") int id) {
		service.delete(id);
		return "redirect:/ProductionStepslist";
	}
}
