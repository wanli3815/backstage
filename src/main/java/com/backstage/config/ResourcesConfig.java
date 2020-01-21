package com.backstage.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description: 映射
 * @author: wanli3815@163.com
 * @create: 2020-01-20 15:45
 **/
@Configuration
public class ResourcesConfig implements WebMvcConfigurer {
    @Value(value = "${backstage.uploadpath}")
    private String uploadPath;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        /** 文件上传路径 */
        registry.addResourceHandler("/uploads/**").addResourceLocations("file:" + uploadPath);

        /** swagger配置 */
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
