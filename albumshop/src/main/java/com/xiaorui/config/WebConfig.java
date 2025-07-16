package com.xiaorui.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebConfig 配置类
 * 用于配置 Spring MVC 的相关设置，例如跨域支持。
 */
@Configuration // 声明为配置类
public class WebConfig implements WebMvcConfigurer {

    /**
     * 配置跨域支持（CORS）
     * 允许前端开发环境通过跨域访问后端服务。
     *
     * @param registry 跨域注册对象
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 允许所有路径进行跨域请求
                .allowedOrigins("http://localhost:8081") // 允许跨域请求的来源（Vue 开发环境）
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 允许的 HTTP 方法
                .allowedHeaders("*") // 允许的请求头
                .allowCredentials(true); // 允许携带认证信息（如 Cookies）
    }
}
