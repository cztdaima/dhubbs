package com.dhu.bbs.configuration;

import com.dhu.bbs.interceptor.LoginRequiredInterceptor;
import com.dhu.bbs.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Component
public class WebConfiguration extends WebMvcConfigurerAdapter {
    @Autowired
    PassportInterceptor PassportInterceptor;

    @Autowired
    LoginRequiredInterceptor loginRequiredInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){

        registry.addInterceptor(PassportInterceptor);
        registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("/post","/profile/*");
        super.addInterceptors(registry);
    }
}
