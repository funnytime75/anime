package org.xu.novel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.xu.novel.interceptor.AuthInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    @SuppressWarnings("null")
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET","POST","PUT","PATCH","DELETE")
                .allowedHeaders("*")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
        .maxAge(3600);
    }
/*
拦截器效果实现
 */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor())
                .addPathPatterns("/reader/**") // 只拦截reader路径下的请求
                .excludePathPatterns(
                        "/reader/login",
                        "/reader/register",
                        "/reader/home",
                        "/reader/logout",
                        "/reader/content",
                        "/reader/search"
                );
    }
}