package com.svelteup.app.backend.security.controllers;

import com.svelteup.app.backend.aws.s3.services.SImageS3;
import com.svelteup.app.backend.dtostores.AppInitDto;
import com.svelteup.app.backend.modelcontroller.controllers.interfaces.ILogInOutAuthenticateController;
import com.svelteup.app.backend.notificationmessage.services.notifications.SOrderStatusNotification;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import com.svelteup.app.backend.profile.services.SUserProfile;
import com.svelteup.app.backend.userlifecycle.services.NavDtoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.naming.OperationNotSupportedException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.NotSupportedException;
import java.io.IOException;

@RestController()
@AllArgsConstructor
public class LogInOutAuthenticateController implements ILogInOutAuthenticateController {

    protected final SOrderStatusNotification orderStatusNotificationService;
    protected final SUserProfile sUserProfile;
    protected final SImageS3 sImageS3;
    protected final AuthenticationManager providerManager;
    protected final NavDtoService navDtoService;


    @Override
    public ResponseEntity<AppInitDto> authenticate(SvelteUpUser user) throws OperationNotSupportedException, NotSupportedException, IOException {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        HttpSession requestSession = request.getSession(true);

        Authentication usernameAndPasswordToken = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
        usernameAndPasswordToken = providerManager.authenticate(usernameAndPasswordToken);
        SecurityContextHolder.getContext().setAuthentication(usernameAndPasswordToken);
        requestSession.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());


        AppInitDto dto =  navDtoService.buildNavStoreDtoWithNotifications(user);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<AppInitDto> onMountAuthenticate(SvelteUpUser user) throws OperationNotSupportedException, NotSupportedException, IOException {
        AppInitDto dto = navDtoService.buildNavStoreDtoWithNotifications(user);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<AppInitDto> login(SvelteUpUser user, HttpServletRequest request, HttpServletResponse response) throws OperationNotSupportedException, IOException, NotSupportedException {
        return this.authenticate(user);
    }



    @Override
    public void logout(HttpServletRequest sessionRequest, HttpServletResponse httpServletResponse) {
        HttpSession requestSession = sessionRequest.getSession(false);
        Cookie logout = new Cookie("SESSION", null);
        logout.setHttpOnly(true);

        if(requestSession != null) {
            requestSession.invalidate();
        }
    }
}
