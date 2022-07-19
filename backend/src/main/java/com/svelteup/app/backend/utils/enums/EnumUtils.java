package com.svelteup.app.backend.utils.enums;

import com.svelteup.app.backend.notificationmessage.models.NotificationEnums;

public class EnumUtils {
    public static  final void throwExceptionIfIndexOutOfBounds(Integer enumOrdinal, Object [] array)
    {
        if(enumOrdinal > array.length)
            throw new IndexOutOfBoundsException(NotificationEnums.OutOfIndexEnumExceptionMessage);
    }
}
