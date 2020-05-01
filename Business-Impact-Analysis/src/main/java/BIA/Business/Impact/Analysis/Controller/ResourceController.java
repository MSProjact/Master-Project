package BIA.Business.Impact.Analysis.Controller;

import BIA.Business.Impact.Analysis.Model.Resource;
import BIA.Business.Impact.Analysis.Model.Role;
import BIA.Business.Impact.Analysis.Service.ProductsService;
import BIA.Business.Impact.Analysis.Service.ResourcesService;
import BIA.Business.Impact.Analysis.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ResourceController {

    @Autowired
    private ResourcesService service;

    @Autowired
    private ProductsService productsService;

    @RequestMapping("/resources/list")
    public String viewHomePage(Model model, HttpServletRequest request) {
        model.addAttribute("Resourceslist", service.getResourceListForManageResources());
        return "manage-resources";
    }

    @RequestMapping(value = "/resource/add", method = RequestMethod.GET)
    public String showNewResourcesPage(Model model, HttpServletRequest request) {
        UserUtil.checkUserRights(request, Role.EMPLOYEE);
        model.addAttribute("Resources",  new Resource());
        model.addAttribute("Productslist", productsService.listAll());

        return "add-resource";
    }

    @RequestMapping(value = "/saveResources", method = RequestMethod.POST)
    public String saveResources(@ModelAttribute("Resources") Resource Resources, HttpServletRequest request) {
        service.save(Resources);
        return "redirect:/resources/list";
    }

    @RequestMapping("/resource/{id}/edit")
    public String getResourceEditPage(@PathVariable(name = "id") String id,
                                      Model model,
                                      HttpServletRequest request) {
        UserUtil.checkUserRights(request, Role.MANAGER);
        model.addAttribute("Resources", service.get(id));
        return "edit-resource";
    }

    @RequestMapping("/DeleteResources/{id}")
    public String deleteResources(@PathVariable(name = "id") String id, HttpServletRequest request) {
        UserUtil.checkUserRights(request, Role.MANAGER);
        service.delete(id);
        return "redirect:/resources/list";
    }
}
