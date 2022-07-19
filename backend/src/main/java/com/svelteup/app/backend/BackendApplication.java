package com.svelteup.app.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.session.FlushMode;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

@SpringBootApplication
@PropertySource("classpath:/braintree/braintree.properties")
@EnableJpaRepositories(queryLookupStrategy = QueryLookupStrategy.Key.CREATE_IF_NOT_FOUND)
@EnableWebSecurity(debug = true)
@EnableTransactionManagement
@EnableSwagger2
@EnableRedisHttpSession(flushMode = FlushMode.IMMEDIATE)
@EnableAspectJAutoProxy()
public class BackendApplication {

    public static void main(String[] args) throws FileNotFoundException {
        SpringApplication.run(BackendApplication.class, args);
    }

}

