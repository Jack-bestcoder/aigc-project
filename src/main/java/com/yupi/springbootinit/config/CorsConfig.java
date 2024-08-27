        package com.yupi.springbootinit.config;

        import org.springframework.context.annotation.Configuration;
        import org.springframework.web.servlet.config.annotation.CorsRegistry;
        import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

        /**
         * 全局跨域配置-解决方案不优雅-后期通过NGINX处理
         *

         */
        @Configuration
        public class CorsConfig implements WebMvcConfigurer {

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // 覆盖所有请求
                registry.addMapping("/**")
                        // 允许发送 Cookie
                        .allowCredentials(true)
                        // 放行哪些域名（必须用 patterns，否则 * 会和 allowCredentials 冲突）
                        .allowedOriginPatterns("http://120.76.47.158:8200","http://47.121.31.131:80")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .exposedHeaders("*");
            }
        }
