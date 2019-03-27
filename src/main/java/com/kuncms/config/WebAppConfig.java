package com.kuncms.config;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.kuncms.config.interceptors.LoginInterceptor;

@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {

    //@Value("${cbs.centos}")
    //private String mImagesPath;
	 @Autowired  
	 LoginInterceptor loginInterceptor; 
	 
	 /** 
	     * 不需要登录拦截的url:登录注册和验证码 
	     */  
	final String[] notLoginInterceptPaths = {"signin","/login/**","/index/**","/register/**","/kaptcha.jpg/**","/kaptcha/**"};
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        if(mImagesPath.equals("") || mImagesPath.equals("${cbs.imagesPath}")){
//               String imagesPath = WebAppConfig.class.getClassLoader().getResource("").getPath();
//               if(imagesPath.indexOf(".jar")>0){
//                imagesPath = imagesPath.substring(0, imagesPath.indexOf(".jar"));
//               }else if(imagesPath.indexOf("classes")>0){
//                imagesPath = "file:"+imagesPath.substring(0, imagesPath.indexOf("classes"));
//               }
//               imagesPath = imagesPath.substring(0, imagesPath.lastIndexOf("/"))+"/images/";
//               mImagesPath = imagesPath;
//              }
//        System.out.println();
//              LoggerFactory.getLogger(WebAppConfig.class).info("imagesPath="+mImagesPath);
    	 registry.addResourceHandler("/images/**").addResourceLocations("file:/usr/local/kun_cms/coverphoto/","file:/usr/local/kun_cms/thun/");
         //registry.addResourceHandler("/images/**").addResourceLocations("file:/usr/local/kun_cms/thun/");
       
       
    }
    
    @Override  
    public void addInterceptors(InterceptorRegistry registry) {  
        // 日志拦截器  
        //registry.addInterceptor(logInterceptor).addPathPatterns("/**");  
        // 登录拦截器  
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns(notLoginInterceptPaths);  
    }  
  
    @Override  
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {  
        configurer.enable();  
    }  
  
    @Bean  
    public InternalResourceViewResolver viewResolver() {  
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();  
        resolver.setPrefix("/templates/");  
        resolver.setSuffix(".html");  
        resolver.setViewClass(JstlView.class);  
        return resolver;  
    }  
  
    @Override  
    public void addViewControllers(ViewControllerRegistry registry) {  
  
    }  
}