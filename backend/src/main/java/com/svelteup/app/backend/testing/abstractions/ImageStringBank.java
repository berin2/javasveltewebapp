package com.svelteup.app.backend.testing.abstractions;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;

public class ImageStringBank {
    public static String productImage;
    public static String profileImage;
    public static final String PNG_PREFIX  = "data:image/png;base64,";

    static {
        try
        {
            Resource textFile = new ClassPathResource("/static/images/imgText.txt");
            Resource profileImageFile  = new ClassPathResource("/static/images/profileImg.txt");
            productImage = PNG_PREFIX + StreamUtils.copyToString(textFile.getInputStream(), StandardCharsets.UTF_8);
            profileImage = PNG_PREFIX + StreamUtils.copyToString(profileImageFile.getInputStream(),StandardCharsets.UTF_8);

        }
        catch (Exception e) { }
    }
}
