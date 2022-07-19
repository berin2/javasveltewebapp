package com.svelteup.app.backend.security.configuration;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.*;

@Component
@EnableWebMvc
public class AppMvcConfigurer implements WebMvcConfigurer {

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/META-INF/resources",
            "classpath:/META-INF/resources/static/", "classpath:/META-INF/public/" ,"classpath:/static/images/"};

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }

    @Override
        public void addCorsMappings(CorsRegistry registry) {

            registry.addMapping("/**")
                    .allowedOriginPatterns("http://localhost:5000")
                    .allowCredentials(true)
                    .allowedMethods(HttpMethod.GET.name(),HttpMethod.POST.name(),HttpMethod.DELETE.name(),HttpMethod.DELETE.name(),HttpMethod.PATCH.name(), HttpMethod.OPTIONS.name(),HttpMethod.PUT.name());

        }
}
