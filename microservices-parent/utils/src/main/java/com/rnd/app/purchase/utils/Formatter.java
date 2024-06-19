package com.rnd.app.purchase.utils;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Date;

@Component
public class Formatter implements IFormat {

    @Override
    public String formatAmount(BigDecimal value, LocaleEnum locale) {
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(locale.getCode());
        Currency currency = Currency.getInstance(locale.getCode());
        return currency.getSymbol() + " " + decimalFormat.format(value.doubleValue());
    }

    @Override
    public String formatDate(Date value, LocaleEnum locale) {
        return "";
    }
}
