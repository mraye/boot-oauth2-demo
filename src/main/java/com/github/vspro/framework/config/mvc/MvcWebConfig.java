package com.github.vspro.framework.config.mvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

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


    //如果采用这种方式，dispatcherServlet中只有一个ViewResolver:BeanNameViewResolver
    //因为使用的是JSP，需要使用InternalResourceViewResolver
    //因此会解析不了对应的JSP页面

    /**
     * 报错
     * 2018-09-09 09:16:15.842 o.s.web.servlet.DispatcherServlet        : Last-Modified value for [/login] is: -1
     * 2018-09-09 09:16:15.876 o.s.w.servlet.view.BeanNameViewResolver  : No matching bean found for view name 'login'
     * 2018-09-09 09:16:15.885 o.s.web.servlet.DispatcherServlet        : Could not complete request
     */
//    @Override
//    public void configureViewResolvers(ViewResolverRegistry registry) {
//        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//        resolver.setPrefix("/WEB-INF/views/");
//        resolver.setSuffix(".jsp");
//        resolver.setOrder(Ordered.LOWEST_PRECEDENCE);
//        registry.viewResolver(resolver);
//    }


//  以这种方式填加ViewResolver，之后DispatcherServlet中viewResolvers就
//  BeanNameViewResolver和InternalResourceViewResolver
//  并且InternalResourceViewResolver的优先级比BeanNameViewResolver的更低
    @Bean
    public ViewResolver iRViewResolver(){
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        //设置优先级，值越低，优先级越高
        //让所有处理不了的VieResolver最后都交给InternalResourceViewResolver处理
        resolver.setOrder(Ordered.LOWEST_PRECEDENCE);
        return resolver;
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //指定了静态资源文件的位置
        registry.addResourceHandler("/static/**").addResourceLocations("/WEB-INF/resources/");
    }
}
