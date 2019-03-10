package com.kuncms;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class KunCmsApplication{

	

	public static void main(String[] args) {
		SpringApplication.run(KunCmsApplication.class, args);
	}
	
	
	/**
	     * 文件上传配置
	     * @return
	     */
	@Bean
	public MultipartConfigElement multipartConfigElement() {
				MultipartConfigFactory factory = new MultipartConfigFactory();
				//文件最大
				 factory.setMaxFileSize("10240KB"); //KB,MB
				/// 设置总上传数据总大小
				 factory.setMaxRequestSize("102400KB");
				 return factory.createMultipartConfig();
	}
	
}
 