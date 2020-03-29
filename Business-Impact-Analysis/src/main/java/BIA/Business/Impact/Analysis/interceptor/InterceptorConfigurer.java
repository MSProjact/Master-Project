package BIA.Business.Impact.Analysis.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * All interceptors has been added here.
 * Now, in this project, there is only one inpterceptor "RootInterceptor".
 * An instance of RootInterceptor has been registered in Spring InterceptorRegistry.
 * 
 */
@Component
public class InterceptorConfigurer implements WebMvcConfigurer {

	@Autowired
	RootInterceptor rootInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(rootInterceptor);
	}
}