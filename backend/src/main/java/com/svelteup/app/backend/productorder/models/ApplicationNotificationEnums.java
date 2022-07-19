package com.svelteup.app.backend.productorder.models;

import com.svelteup.app.backend.utils.enums.EnumToInteger;
import com.svelteup.app.backend.utils.enums.EnumUtils;

import java.util.concurrent.ThreadLocalRandom;

/*
* ApplicationNotificationEnums represents the complete set of states a productorder can be in.
* */
public enum ApplicationNotificationEnums implements EnumToInteger{
    ORDER_PLACED,
    ORDER_ACCEPTED,
    ORDER_CANCEL_REQUESTED,
    ORDER_CANCEL_ACCEPTED,
    ORDER_CANCEL_PROCESSED,
    UTIL_INSTANCE;

    public static ApplicationNotificationEnums fromInteger(Integer enumOrdinal) throws IndexOutOfBoundsException {
        EnumUtils.throwExceptionIfIndexOutOfBounds(enumOrdinal,values());
        return values()[enumOrdinal];
    }

    public static ApplicationNotificationEnums randomEnumValue()
    {
        Integer  randomInt = ThreadLocalRandom.current().nextInt(0, 5);
        return fromInteger(randomInt);
    }
}
