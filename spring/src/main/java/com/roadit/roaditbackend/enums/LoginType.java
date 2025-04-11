package com.roadit.roaditbackend.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum LoginType {
    ROADIT, GOOGLE;

    @JsonCreator
    public static LoginType from(String value) {
        return LoginType.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String getValue() {
        return name();
    }
}