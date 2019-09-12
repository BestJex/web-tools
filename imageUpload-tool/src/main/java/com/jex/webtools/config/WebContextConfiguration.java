package com.jex.webtools.config;

import com.jex.webtools.annotation.RenamingProcessor;
import com.jex.webtools.interceptor.CommonInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
public class WebContextConfiguration extends WebMvcConfigurationSupport {

    private final static Logger logger = LoggerFactory.getLogger(WebContextConfiguration.class);

    /**
     * 配置静态资源处理
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        logger.info("自定义静态资源目录，此处功能用于静态资源映射");
        //映射到了Windows下 的 D:/uploads/
        registry.addResourceHandler("/uploads/**").addResourceLocations("file:D:/uploads/");
        //Mac或Linux下(没有CDEF盘符)，映射到了Windows下 的 /Users/uploads/
        registry.addResourceHandler("/uploads2/**").addResourceLocations("file:/Users/uploads/");
        logger.info("自定义静态资源目录，此处功能用于文件映射");
        super.addResourceHandlers(registry);
    }

    /**
     * 以前要访问一个页面需要先创建个Controller控制类，在写方法跳转到页面
     * 在这里配置后就不需要那么麻烦了，直接访问http://localhost:8081/toLogin就跳转到login.html页面了
     *
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/tologin").setViewName("login");
        super.addViewControllers(registry);
    }

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new CommonInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

    /**
     * 设置post数据到实体类的映射
     * @param argumentResolvers
     */
    @Override
    protected void addArgumentResolvers( List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(renamingProcessor());
    }

    @Bean
    protected RenamingProcessor renamingProcessor() {
        return new RenamingProcessor(true);
    }

}