package com.svelteup.app.backend.utils.enums;

/*
* EnumToInteger<EnumType> is static marker interface used to communicate to developers that the implementing  class
* should provide a static fromInteger method that returns an EnumType based om the integer idnex passed to the method
 * */
public interface EnumToInteger<EnumType> {
    public static final String OutOfIndexEnumExceptionMessage = "FromInteger method was asked to produce an enum value with an integer whose value is out of the allowed range of enum values.";
}
