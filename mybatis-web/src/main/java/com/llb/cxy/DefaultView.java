package com.llb.cxy;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
/*import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;*/
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class DefaultView extends WebMvcConfigurationSupport {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		/*if (SecurityUtils.getSubject().getPrincipal() != null) {
			registry.addViewController("/").setViewName("forward:/index");
		} else {
			registry.addViewController("/").setViewName("forward:/login");
		}*/
		/*Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(null != auth) {
			if (!auth.getName().
					equals("anonymousUser")) {
				System.out.println("LOGGED IN");
				registry.addViewController("/").setViewName("forward:/index");
			} else {
				System.out.println("NOT LOGGED IN");
				registry.addViewController("/").setViewName("forward:/login.jsp");
			}
		} else {
			registry.addViewController("/").setViewName("forward:/login.jsp");
		}
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);*/
		super.addViewControllers(registry);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 静态资源
		registry.addResourceHandler("/**").addResourceLocations("/WEB-INF/resources/");
		registry.addResourceHandler("/js/**").addResourceLocations("/WEB-INF/js/");
		registry.addResourceHandler("/css/**").addResourceLocations("/WEB-INF/css/");
		registry.addResourceHandler("/images/**").addResourceLocations("/WEB-INF/images/");
		// 静态资源转换
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");

		super.addResourceHandlers(registry);
	}
	
//	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
//        ObjectMapper objectMapper = new ObjectMapper();
//        /**
//         * 序列换成json时,将所有的long变成string
//         * 因为js中得数字类型不能包含所有的java long值
//         */
//        SimpleModule simpleModule = new SimpleModule();
//        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
//        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
//        objectMapper.registerModule(simpleModule);
//        jackson2HttpMessageConverter.setObjectMapper(objectMapper);
//        converters.add(jackson2HttpMessageConverter);
//    }
	

}