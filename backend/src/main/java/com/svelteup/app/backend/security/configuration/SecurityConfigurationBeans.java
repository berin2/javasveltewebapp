package com.svelteup.app.backend.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import sun.security.util.Password;

/*
* Stores Spring-Security related beans.
* */
@Configuration("security-config")
public class SecurityConfigurationBeans {


    @Bean
    public SpringSessionRememberMeServices rememberMeServices() {
        SpringSessionRememberMeServices rememberMeServices =
                new SpringSessionRememberMeServices();
        // optionally customize
        rememberMeServices.setAlwaysRemember(true);
        return rememberMeServices;
    }


    @Bean
    public SpringSessionBackedSessionRegistry getSessionRegistry(RedisIndexedSessionRepository sessionRepository)
    {
        SpringSessionBackedSessionRegistry sessionBackedSessionRegistry = new SpringSessionBackedSessionRegistry(sessionRepository);
        return sessionBackedSessionRegistry;
    }



    @Bean
    public PasswordEncoder getNoOpPasswordEncoder()
    {
        return NoOpPasswordEncoder.getInstance();
    }
}
