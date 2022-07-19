package com.svelteup.app.backend.utils.objects;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public abstract class AbstractThymeTemplateMapper {

    public static Map<String,Object> getVerificationTemplateMap(String username, UUID emailToken)
    {
        Map<String,Object> verificationTemplate = new TreeMap<>();
        verificationTemplate.put("username",username);
        verificationTemplate.put("emailToken",emailToken.toString());
        return  verificationTemplate;
    }
}
