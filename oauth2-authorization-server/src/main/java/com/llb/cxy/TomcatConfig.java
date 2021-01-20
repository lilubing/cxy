package com.llb.cxy;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * @ClassName TomcatConfig
 * @Description TODO
 * @Author moon
 * @Date 2019/11/7 11:35
 * @Version 1.0
 **/
@Configuration
public class TomcatConfig {
    /*@Bean
    public EmbeddedServletContainerFactory embeddedServletContainerFactory() {
        ConfigurableEmbeddedServletContainer factory = new TomcatEmbeddedServletContainerFactory();
        factory.setDocumentRoot(new File("F:\\demo\\my\\cxy\\cxy-authorization-server\\src\\main\\webapp\\"));
        return (EmbeddedServletContainerFactory) factory;
    }*/

    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        //tomcat.addConnectorCustomizers(new GwsTomcatConnectionCustomizer());
        factory.setDocumentRoot(new File("/Users/work/workspace/cxy/oauth2-authorization-server/src/main/webapp"));
        return factory;
    }
}
