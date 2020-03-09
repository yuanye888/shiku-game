package com.shiku.robot.shikugame.base.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    /**
     * 保障在spring加载的时候注入拦截器，可以在拦截器中使用业务service。
     *
     * @return
     */
    @Bean
    AuthSecurityInterceptor authSecurityInterceptor() {
        return new AuthSecurityInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
        // 可添加多个
        interceptorRegistry.addInterceptor(authSecurityInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/getCurrentTime", "/redPacket/**",
                        "/*.html", "/css/**", "/js/**", "/images/**",
                        "/fonts/**", "/images/**", "/lib/**", "/pagejs/**");
    }
}
