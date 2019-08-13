package com.boraji.tutorial.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

   @Override
   public void configureViewResolvers(ViewResolverRegistry registry) {
      registry.jsp().prefix("/WEB-INF/views/").suffix(".jsp");
   }

   @Override
   public void addViewControllers(ViewControllerRegistry registry) {
      registry.addViewController("/login").setViewName("/index");
   }
}
