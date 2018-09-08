package com.github.vspro.framework.config.mvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Created  on 2018/9/8.
 */
@Configuration
@EnableWebMvc
public class MvcWebConfig implements WebMvcConfigurer {

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }


    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        registry.viewResolver(resolver);
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //指定了静态资源文件的位置
        registry.addResourceHandler("/static/**")
                .addResourceLocations("/WEB-INF/resources/");
    }
}
