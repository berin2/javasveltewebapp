package com.svelteup.app.backend.utils.configuration;

import com.svelteup.app.backend.utils.objects.HostDescriptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.net.UnknownHostException;

/**
 * SharedConfiguration returns shared beans that are usable by  all submodules of the app.
 */
@Configuration
public class SharedConfiguration {
    @Bean
    public HostDescriptor getHostDescriptor(Environment env) throws UnknownHostException {
        return new HostDescriptor(env);
    }
}
