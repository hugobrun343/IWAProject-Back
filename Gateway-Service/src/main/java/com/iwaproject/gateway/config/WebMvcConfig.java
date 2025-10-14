package com.iwaproject.gateway.config;

import com.iwaproject.gateway.filter.RateLimitFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC configuration for Gateway.
 * Registers rate limiting interceptor.
 */
@Configuration
@Profile("!test")
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * Rate limit filter.
     */
    private final RateLimitFilter rateLimitFilter;

    /**
     * Constructor.
     *
     * @param filter the rate limit filter
     */
    public WebMvcConfig(final RateLimitFilter filter) {
        this.rateLimitFilter = filter;
    }

    /**
     * Add interceptors to the registry.
     *
     * @param registry the interceptor registry
     */
    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(rateLimitFilter)
                .addPathPatterns("/api/**");
    }
}

