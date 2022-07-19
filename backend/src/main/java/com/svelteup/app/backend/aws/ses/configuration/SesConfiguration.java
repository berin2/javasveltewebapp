package com.svelteup.app.backend.aws.ses.configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;


/**
 * SesConfiguration returns Amazon SES required beans.
 */
@Configuration
public class SesConfiguration {

    @Bean()
    public AmazonSimpleEmailService getSesClient(AWSCredentials awsCredentials)
    {
        AmazonSimpleEmailService client =
                AmazonSimpleEmailServiceClientBuilder.standard()
                        .withCredentials( new AWSStaticCredentialsProvider(awsCredentials))
                        .withRegion(Regions.US_WEST_1)
                        .build();

        return client;
    }
}
