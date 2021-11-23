package cn.xqs.blog.config;

import cn.xqs.blog.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")                                       //拦截路径中带有admin的所有请求
                .excludePathPatterns("/admin/", "/admin/login", "/admin/checkCode");  //登录请求，获取验证码的请求不拦截
    }
}
