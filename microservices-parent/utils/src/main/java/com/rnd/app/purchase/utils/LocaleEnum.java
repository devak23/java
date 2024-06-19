package com.rnd.app.purchase.utils;

import java.util.Locale;

public enum LocaleEnum {
    USD ("en", "US"),
    INR ("in", "IN"),
    JPY ("ja", "jp"),
    NOK ("no", "NO"),
    CHN ("zh", "CN"),
    ESP ("es", "ES"),
    DEU ("de", "DE");

    private final String language;
    private final String country;


    LocaleEnum(String language, String country) {
        this.language = language;
        this.country = country;
    }

    public Locale getCode() {
        return new Locale(language, country);
    }
}
