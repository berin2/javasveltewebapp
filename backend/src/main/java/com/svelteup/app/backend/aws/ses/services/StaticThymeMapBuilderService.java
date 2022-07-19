package com.svelteup.app.backend.aws.ses.services;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public abstract class StaticThymeMapBuilderService {
    public static  Map<String,Object> buildVerificationMap(String username, UUID tokenId)
    {
        Map<String,Object> templateValues = new TreeMap<>();
        templateValues.put("username",username);
        templateValues.put("emailToken",tokenId.toString());
        return templateValues;
    }
}
