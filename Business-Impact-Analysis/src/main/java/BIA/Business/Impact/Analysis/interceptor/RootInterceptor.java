package BIA.Business.Impact.Analysis.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import BIA.Business.Impact.Analysis.Controller.LoginController;
import BIA.Business.Impact.Analysis.Model.Employees;

/**
 * RootInterceptor is the root interceptor of this spring boot project.
 * Here, the login state has been checked.
 * If the user has logined, it will go to controller, but if the user has not logined, it will direct into login page.
 
 */
@Component
public class RootInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws IOException {
		// If this request is for resource, it will return true.
		if (handler instanceof ResourceHttpRequestHandler) {
			return true;
		}
		String servletPath = request.getServletPath();
		// If this request is for login page, it will return true.
		if (servletPath.contains("/login"))
			return true;
		// If this request is not for login page, it will check session and login state.
		HttpSession session = request.getSession();
		Employees me = (Employees) session.getAttribute(LoginController.SESSION_ME);
		if (me == null) {
			// This means the user has not logined, so it will redirect into login page.
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + "/login");
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
							  ModelAndView modelAndView) throws Exception {
		Object me = request.getSession().getAttribute("ME");
		if (me != null && modelAndView != null) {
			String role = ((Employees) me).getRole().name();
			modelAndView.addObject("Role", role);
		}
	}

}
