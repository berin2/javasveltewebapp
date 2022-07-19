package com.svelteup.app.backend.notificationmessage.services.notifications;

import com.svelteup.app.backend.aop.aspects.paireduser.OwningUserPairedNonPkEntityAccessCheck;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http403Exception;
import com.svelteup.app.backend.modelcontroller.services.abstractions.SSurrogateEntityOwningUserNonPk;
import com.svelteup.app.backend.notificationmessage.dtos.NotificationDto;
import com.svelteup.app.backend.notificationmessage.events.NotificationEvent;
import com.svelteup.app.backend.notificationmessage.models.Notification;
import com.svelteup.app.backend.notificationmessage.repositories.RNotification;
import com.svelteup.app.backend.productorder.models.ApplicationNotificationEnums;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.naming.OperationNotSupportedException;
import javax.transaction.NotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SOrderStatusNotification extends SSurrogateEntityOwningUserNonPk<Long, Notification> implements OwningUserPairedNonPkEntityAccessCheck<Notification> {

    protected RNotification notificationRepository;

    protected static final String ORDER_PLACED_NOTIFICATION  = "%s has placed an order for %s.";
    protected static final String ORDER_CANCEL_REQUESTED_NOTIFICATION = "%s has requested to cancel their order.";

    protected static final String ORDER_ACCEPTED_NOTIFICATION = "%s has marked the order for %s complete.";
    protected static final String ORDER_CANCEL_APPROVED_NOTIFICATION = "%s has accepted the return for %s.";

    protected static final Integer NOTIFICATION_PAGE_SIZE = 10;
    protected static final Integer MAXIMUM_UNREAD_NOTIFICATION_PAGE = 2;
    protected static final Sort NOTIFICATION_SORT = Sort.by(Sort.Direction.DESC,"notificationId");


    public SOrderStatusNotification(RNotification notificationRepository) {
        super(notificationRepository);
        this.notificationRepository = notificationRepository;
    }

    public List<NotificationDto> getAllUnreadNotificationsForUsername(String authenticationPrincipal) throws OperationNotSupportedException, NotSupportedException {
        List<Notification> notificationDtos = this.notificationRepository.findNotificationByOwningUsernameAndNotificationReadIsFalse(authenticationPrincipal);
        List<NotificationDto> returnList = buildNotificationDtoList(authenticationPrincipal,notificationDtos);


        return returnList;

    }
    public List<NotificationDto> getNotificationPageForUsername(String authenticationPrincipal, Integer pageIndex) throws OperationNotSupportedException, NotSupportedException {

        PageRequest requestedPage = PageRequest.of(pageIndex,NOTIFICATION_PAGE_SIZE,NOTIFICATION_SORT);
        Page<Notification> unreadNotificationPage = this.notificationRepository.getNotificationsByNotificationReadIsFalseAndOwningUsername(authenticationPrincipal,requestedPage);
        List<NotificationDto> returnList = new ArrayList<>();
        List<Notification> pageNotificationList = unreadNotificationPage.toList();

        if(pageNotificationList.size() != 0)
            returnList.addAll(this.buildNotificationDtoList(authenticationPrincipal,pageNotificationList));

        if(pageNotificationList.size() < NOTIFICATION_PAGE_SIZE && pageIndex < MAXIMUM_UNREAD_NOTIFICATION_PAGE)
        {
            pageNotificationList = this.notificationRepository
                    .getNotificationByNotificationReadIsTrueAndOwningUsername(authenticationPrincipal,requestedPage)
                    .toList();
            returnList.addAll(this.buildNotificationDtoList(authenticationPrincipal,pageNotificationList));
        }

        return returnList;
    }

    protected List<NotificationDto> buildNotificationDtoList(String authenticationPrincipal, List<Notification> notifications) throws NotSupportedException, OperationNotSupportedException {

        List<NotificationDto> returnList = new ArrayList<>();
        NotificationDto notificationiterator = null;
        String notificationMessageIterator = null;

        for(Notification elementNotification : notifications)
        {
            elementNotification.setNotificationRead(true);
            this.accessCheck(authenticationPrincipal,elementNotification);
            notificationMessageIterator = this.buildOrderStatusMessage(elementNotification.getNotificationSubject(),elementNotification.getNotificationNoun(),elementNotification.getNotificationTypeEnumOrdinalValue());
            notificationiterator = new NotificationDto(elementNotification,notificationMessageIterator);
            returnList.add(notificationiterator);
            this.notificationRepository.save(elementNotification);
        }

        return returnList;
    }



    public void postNotificationEvent(NotificationEvent notificationEvent)
    {
        Notification newNotification = new Notification(notificationEvent);
        this.notificationRepository.save(newNotification);
    }


    /**
     * @param subject The subject target of the notification message string. Usually refers to notification sender.
     * @param noun    The target noun of the notifcation such as a product name, product id, and so on.
     * @param enumOrdinalValue The Integer value of the ApplicationNotificationEnums.
     * @return returns the orderstatusmessage to send back to the user.
     * @throws OperationNotSupportedException
     */
    protected String buildOrderStatusMessage(String subject,String noun, Integer enumOrdinalValue) throws OperationNotSupportedException {
        String returnString = null;
        String formatString =  null;
        ApplicationNotificationEnums notificationEnum = ApplicationNotificationEnums.fromInteger(enumOrdinalValue);

        switch(notificationEnum)
        {
            case ORDER_PLACED: formatString = ORDER_PLACED_NOTIFICATION; break;
            case ORDER_ACCEPTED: formatString = ORDER_ACCEPTED_NOTIFICATION; break;
            case ORDER_CANCEL_REQUESTED: formatString  = ORDER_CANCEL_REQUESTED_NOTIFICATION; break;
            case ORDER_CANCEL_ACCEPTED: formatString = ORDER_CANCEL_APPROVED_NOTIFICATION; break;
            default:
                throw new OperationNotSupportedException("buildOrderStatusMessage was passed an enumOrdinal value of the range of supported ordinals for enum " + ApplicationNotificationEnums.class.toString());
        }

        returnString = String.format(formatString,subject,noun);

        return returnString;
    }

    @Override
    public Notification afterReturningOwningUserPairedNonPrimaryKeyPermissionCheck(String authenticatedUser, UUID entitySurrogateId) throws Http403Exception, NotSupportedException {
        return this.findBySurrogateId(entitySurrogateId);
    }

    /**
     * afterReturningIsOwningUserCheck is used to identify if the user is the owning or secondary owning user.
     *
     * @param authenticatedUser The user to check against the entity.
     * @param entitySurrogateId The entity  surrogateId
     * @return a true Boolean indicating that the authenticatedUser is the owningUser, or false if the user
     * is the secondary user. Before calling this method, it's important to ensure the user is either the owningUser
     * or the secondaryOwningUser.
     */
    @Override
    public Notification afterReturningIsOwningUserOrSecondaryUserCheck(String authenticatedUser, UUID entitySurrogateId) throws NotSupportedException {
        return this.findBySurrogateId(entitySurrogateId);
    }

    @Override
    public Notification beforeOwningUserPairedNonPrimaryKeyPermissionCheck(String authenticatedUser, Notification entity) throws Http403Exception, NotSupportedException {
        return entity;
    }

    @Override
    public Notification beforeOwningUserPairedNonPrimaryKeyIsOwningUserCheck(String authenticatedUser, Notification entity) throws Http403Exception, NotSupportedException {
        return entity;
    }

    @Override
    public Notification beforeOwningUserPairedNonPrimaryKeyIsSecondaryOwningUserCheck(String authenticatedUser, Notification entity) throws Http403Exception, NotSupportedException {
        return entity;
    }

    /**
     * beforeOwningUserPairedNonPrimaryKeyIsNotPrimaryAndSecondaryOwningUserCheck ensures a user is neither the primary or secondary owning user.
     *
     * @param authenticatedUser the user to permission check.
     * @param entity            the user to check permissions for.
     * @return entity the entity passed as a parameter.
     * @throws Http403Exception      if the user is not listed as secondaryUser and primaryOwningUser.
     * @throws NotSupportedException if the method call is not supported in implementing service
     */
    @Override
    public Notification beforeOwningUserPairedNonPrimaryKeyIsNotPrimaryAndSecondaryOwningUserCheck(String authenticatedUser, Notification entity) throws Http403Exception, NotSupportedException {
        return entity;
    }
}
