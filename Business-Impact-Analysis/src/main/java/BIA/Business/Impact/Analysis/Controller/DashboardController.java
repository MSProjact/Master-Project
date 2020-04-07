package BIA.Business.Impact.Analysis.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @date 2020/02/26 16:30:10
 * 
 * This controller was created for the dashboard/index page.
 */
@Controller("/dashboardController")
public class DashboardController {

	/**
	 * View dashboard.
	 *
	 * @return the dashboard page name String. Which have the navigation bar for all URLs.
	 */
	@RequestMapping("/")
	public String viewDashboard() {
		return "Dashboard";
	}
}
