package com.llb.cxy.boot.config;

/*import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;*/
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * description: SpringMvcWebConfigSupport <br>
 * date: 2020/2/26 13:42 <br>
 * author: Administrator <br>
 * version: 1.0 <br>
 */
//@Configuration
public class SpringMvcWebConfigSupport implements WebMvcConfigurer {
    /**
     * 默认访问的是首页 //保留了SpringBoot的自动配置，也使用了自己的SpringMmv的配置
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
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
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        WebMvcConfigurer.super.addViewControllers(registry);*/

        /*registry.addViewController("/").setViewName("index");//前拼templates，后拼.html
        registry.addViewController("/index.html").setViewName("index");//浏览器发送/请求来到login.html页面，不用写controller控制层的请求方法了
        */
    }

    /**
     * 将static下面的js，css文件加载出来
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");

        // 静态资源
        registry.addResourceHandler("/**").addResourceLocations("/WEB-INF/resources/");
        registry.addResourceHandler("/js/**").addResourceLocations("/WEB-INF/js/");
        registry.addResourceHandler("/css/**").addResourceLocations("/WEB-INF/css/");
        registry.addResourceHandler("/images/**").addResourceLocations("/WEB-INF/images/");
        // 静态资源转换
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");

        //super.addResourceHandlers(registry);

    }
}