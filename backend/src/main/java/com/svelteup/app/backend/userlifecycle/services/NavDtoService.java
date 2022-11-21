package com.svelteup.app.backend.userlifecycle.services;

import com.svelteup.app.backend.api.ApplicationApi;
import com.svelteup.app.backend.aws.s3.services.SImageS3;
import com.svelteup.app.backend.dtostores.AppInitDto;
import com.svelteup.app.backend.notificationmessage.services.messagechats.SMessageChat;
import com.svelteup.app.backend.notificationmessage.services.notifications.SOrderStatusNotification;
import com.svelteup.app.backend.profile.models.SvelteUpUserProfile;
import com.svelteup.app.backend.profile.services.SUserProfile;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.naming.OperationNotSupportedException;
import javax.transaction.NotSupportedException;
import java.io.IOException;

@Service()
@RequiredArgsConstructor()
public class NavDtoService {

    protected final SImageS3 s3Service;
    protected final SOrderStatusNotification orderStatusNotificationService;
    protected final  SMessageChat sMessageChat;
    protected final SUserProfile userProfileService;

    @Value("${spring.profiles.active}")
    String profile;

    public AppInitDto buildNavStoreDtoWithNotifications(SvelteUpUser user) throws IOException, OperationNotSupportedException, NotSupportedException {
        AppInitDto dto = new AppInitDto();
        dto.username = user.getUsername();
        dto.isEmailValidated = user.getIsEmailValidated();
        dto.isIdentityVerified = user.getIsIdentityValidated();
        dto.notifications = orderStatusNotificationService.getNotificationPageForUsername(user.getUsername(),0);
        dto.messages =  this.sMessageChat.getParticipatingMessageChatsForUser(user.getUsername(),null);
        String awsImage = null;
        if(profile.equals(ApplicationApi.DEV_PROFILE))
            awsImage = s3Service.getTestProfileImage();
        else
            awsImage = s3Service.getSingleImage(SvelteUpUserProfile.class,user.getUsername(),0,null);

        dto.profile = this.userProfileService.getProfileDto(user.getUsername(),awsImage);

        return dto;
    }

    public AppInitDto buildNavStoreDtoWithoutNotificationsAndWithoutImages(SvelteUpUser user)
    {
        AppInitDto dto = new AppInitDto();
        dto.username = user.getUsername();
        dto.isEmailValidated = user.getIsEmailValidated();
        dto.isIdentityVerified = user.getIsIdentityValidated();
        return dto;
    }
}
