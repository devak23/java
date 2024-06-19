package com.rnd.app.purchase.utils;

import java.math.BigDecimal;
import java.util.Date;

public interface IFormat {
    String formatAmount(BigDecimal value, LocaleEnum locale);
    String formatDate(Date value, LocaleEnum locale);
}
