package com.svelteup.app.backend.apitesting;

import com.svelteup.app.backend.security.models.SvelteUpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.session.Session;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class TestingController {

    @Autowired
    SpringSessionBackedSessionRegistry repo;
    @Autowired
    RedisIndexedSessionRepository sessionRepository;
    @Autowired
    SpringSessionBackedSessionRegistry rgistry;

    @GetMapping("/static/test/{sessionId}/{username}")
    public ResponseEntity<String> getSession(@Autowired HttpRequestResponseHolder req, @PathVariable String sessionId, @PathVariable String username)
    {

        SecurityContext  testUsr = null ;
        for(Session obj  : sessionRepository.findByPrincipalName("test").values())
            testUsr  =(SecurityContext) obj.getAttribute("SPRING_SECURITY_CONTEXT");

        System.out.println(testUsr.toString());
        return ResponseEntity.ok(null);
    }
}
