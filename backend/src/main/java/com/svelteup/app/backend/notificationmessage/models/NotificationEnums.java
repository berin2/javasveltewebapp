package com.svelteup.app.backend.notificationmessage.models;

import com.svelteup.app.backend.utils.enums.EnumToInteger;
import com.svelteup.app.backend.utils.enums.EnumUtils;

/*
* NotificationEnums class specifies the different kinds of notification types a user is able to recieve.
* For example, a user can recieve a notification about an OrderStatus change. Updated
* */
public enum NotificationEnums implements EnumToInteger<NotificationEnums> {
    ORDER_STATUS_CHANGE;

    public static NotificationEnums fromInteger(Integer enumOrdinal) throws IndexOutOfBoundsException {
        EnumUtils.throwExceptionIfIndexOutOfBounds(enumOrdinal,values());
        NotificationEnums returnEnum = values()[enumOrdinal];
        return returnEnum;
    }
}
