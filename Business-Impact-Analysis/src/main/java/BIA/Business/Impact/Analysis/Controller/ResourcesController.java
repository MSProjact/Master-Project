package BIA.Business.Impact.Analysis.Controller;

import BIA.Business.Impact.Analysis.Model.ProductionSteps;
import BIA.Business.Impact.Analysis.Model.Resources;
import BIA.Business.Impact.Analysis.Model.Role;
import BIA.Business.Impact.Analysis.Service.ProductionStepsService;
import BIA.Business.Impact.Analysis.Service.ResourcesService;
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
import java.util.List;

@Controller
public class ResourcesController {

	@Autowired
	private ResourcesService service;
	
	@Autowired
	private ProductionStepsService service1;

	@RequestMapping("/Resourceslist")
	public String viewHomePage(Model model, HttpServletRequest request) {
		List<Resources> Resourceslist = service.listAll();
		model.addAttribute("Resourceslist", Resourceslist);
		return "Manage_Financial_Resources";
	}

	@RequestMapping("/NewResources")
	public String showNewResourcesPage(Model model, HttpServletRequest request) {
		UserUtil.checkUserRights(request, Role.EMPLOYEE);
		Resources Resources = new Resources();
		model.addAttribute("Resources", Resources);
		List<ProductionSteps> ProductionSteps = service1.listAll();
		model.addAttribute("ProductionStepslist", ProductionSteps);
		
		return "Add_FinancialResources";
	}

	@RequestMapping(value = "/saveResources", method = RequestMethod.POST)
	public String saveResources(@ModelAttribute("Resources") Resources Resources, HttpServletRequest request) {
		service.save(Resources);
		return "redirect:/Resourceslist";
	}

	@RequestMapping("/EditResources/{id}")
	public ModelAndView showEditResourcePage(@PathVariable(name = "id") String id, HttpServletRequest request) {
		UserUtil.checkUserRights(request, Role.MANAGER);
		ModelAndView mav = new ModelAndView("Edit_Recourses");
		Resources Resources = service.get(id);
		mav.addObject("Resources", Resources);
		return mav;
	}

	@RequestMapping("/DeleteResources/{id}")
	public String deleteResources(@PathVariable(name = "id") String id, HttpServletRequest request) {
		UserUtil.checkUserRights(request, Role.MANAGER);
		service.delete(id);
		return "redirect:/Resourceslist";
	}
}
