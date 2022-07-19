package com.svelteup.app.backend.utils.services.interfaces;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

/**
 * SThymeLeafTemplate is an interface that can be implemented to return ThymeLeaf Template Maps.
 */
public interface SThymeLeafTemplate {
    default Map<String,Object> getVerificationTemplate(String username, UUID emailToken)
    {
        Map<String,Object> templateMap = new TreeMap<>();
        templateMap.put("emailToken",emailToken.toString());
        templateMap.put("username",username);
        return templateMap;
    }
}
