package org.isabella.revdol.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <pre>
 * @Description:
 * @Aouth: cao_wencao
 * @Date: 2019-01-23 17:28
 * </pre>
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //registry.addResourceHandler("/out_images/**").addResourceLocations("file:/revdol/media/");
        registry.addResourceHandler("/out_images/**").addResourceLocations("http://resource.revdol.club/revdol/media/");
        //registry.addResourceHandler("/out_images/**").addResourceLocations("file:D://【操作间】请开始您新的表演/");
    }
}