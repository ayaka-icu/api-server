package icu.ayaka.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    /**
     * 配置静态资源映射
     **/
    @Component
    public class WebMvcConfig implements WebMvcConfigurer {

        /**
         * springboot 无法直接访问静态资源，需要放开资源访问路径。
         * 添加静态资源文件，外部可以直接访问地址
         */
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        }

    }

}
