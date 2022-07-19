package com.svelteup.app.backend.security.configuration;

import com.svelteup.app.backend.api.ApplicationApi;
import com.svelteup.app.backend.security.filters.ApplicationUsernamePasswordLoginFilter;
import com.svelteup.app.backend.security.filters.SessionRefresherFilter;
import com.svelteup.app.backend.security.models.AccountAuthority;
import com.svelteup.app.backend.security.models.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;
import org.springframework.session.web.http.SessionRepositoryFilter;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component @Order(Ordered.HIGHEST_PRECEDENCE) @DependsOn("security-config")
@RequiredArgsConstructor
public class SecurityConfigurationProd extends WebSecurityConfigurerAdapter {
    private final UserDetailsService accountDetailsService;
    private final PasswordEncoder encoder;
    private final ApplicationUsernamePasswordLoginFilter usernamePasswordLoginFilter;
    private final SpringSessionRememberMeServices springSessionRememberMeServices;
    private final RedisIndexedSessionRepository sessionRepository;
    private final SessionRepositoryFilter redisSessionFilter;
    private final SessionRefresherFilter sessionRefresherFilter;



    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception
    {
        httpSecurity
                .rememberMe((rememberMe) -> rememberMe
                .rememberMeServices(springSessionRememberMeServices))
                .sessionManagement((sessionManagement) ->
                                    sessionManagement
                                            .maximumSessions(3)
                                            .sessionRegistry(new SpringSessionBackedSessionRegistry<>(sessionRepository))
                                            .maxSessionsPreventsLogin(false)
                                            .and()
                                            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)

                                   )
                    .cors().and()
                    .csrf().disable()
                    .headers().frameOptions().disable().and()
                .addFilterBefore(this.usernamePasswordLoginFilter, RememberMeAuthenticationFilter.class)
                .addFilterAfter(this.sessionRefresherFilter,RememberMeAuthenticationFilter.class)
                .authorizeRequests()
                    .antMatchers(HttpMethod.GET,"/static/**").permitAll()
                    .antMatchers(HttpMethod.GET,"/h2-console","/thyme","/aoptest").permitAll()
                    .antMatchers("/api/v1/noauth/**")
                    .anonymous()
                .antMatchers(ApplicationApi.AUTH_FULLY_SETUP + "**")
                .hasAuthority(Authority.FULLY_SETUP_ACCOUNT)

                .antMatchers(
                            ApplicationApi.GET_NOTIFICATIONS,ApplicationApi.LOGIN,ApplicationApi.LOGOUT,ApplicationApi.GET_AUTHENTICATE,ApplicationApi.PUT_ACCOUNT_EMAIL,ApplicationApi.PUT_ACCOUNT_VERIFICATION_TOKEN)
                    .authenticated()
                    .and()
                    .exceptionHandling();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception
    {
        authenticationManagerBuilder
                .userDetailsService(this.accountDetailsService)
                .passwordEncoder(this.encoder);
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

}
