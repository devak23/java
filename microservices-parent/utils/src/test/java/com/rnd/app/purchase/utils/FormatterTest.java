package com.rnd.app.purchase.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;


public class FormatterTest {
    public final BigDecimal AMOUNT = new BigDecimal("2399.99");
    private Formatter formatter;

    @BeforeEach
    public void setUp() {
        formatter = new Formatter();
    }

    @DisplayName("Check formatting for USD")
    @Test
    public void givenAmount_whenFormatterIsUSD_thenAmountIsInUSDFormatAmount() {
        // given - define precondition for test

        // when - perform the desiredAction
        String formattedValue = formatter.formatAmount(AMOUNT, LocaleEnum.USD);

        // then - verify the output
        assertThat(formattedValue).isNotNull();
        assertThat(formattedValue).isEqualTo("$ 2,399.99");
    }

    @DisplayName("Check formatting for JPY")
    @Test
    public void givenAmount_whenFormatterIsJPY_thenAmountIsInJPYFormatAmount() {
        // given - define precondition for test

        // when - perform the desiredAction
        String formattedValue = formatter.formatAmount(AMOUNT, LocaleEnum.JPY);

        // then - verify the output
        assertThat(formattedValue).isNotNull();
        assertThat(formattedValue).isEqualTo("JP¥ 2,399.99");
    }

    @DisplayName("Check formatting for NOK")
    @Test
    public void givenAmount_whenFormatterIsNOK_thenAmountIsInNOKFormatAmount() {
        // given - define precondition for test

        // when - perform the desiredAction
        String formattedValue = formatter.formatAmount(AMOUNT, LocaleEnum.NOK);

        // then - verify the output
        assertThat(formattedValue).isNotNull();
        assertThat(formattedValue).isEqualTo("NOK 2 399,99");
    }

    @DisplayName("Check formatting for EUR")
    @Test
    public void givenAmount_whenFormatterIsEUR_thenAmountIsInEURFormatAmount() {
        // given - define precondition for test

        // when - perform the desiredAction
        String formattedValue = formatter.formatAmount(AMOUNT, LocaleEnum.DEU);

        // then - verify the output
        assertThat(formattedValue).isNotNull();
        assertThat(formattedValue).isEqualTo("€ 2.399,99");
    }

    @DisplayName("Check formatting for CN")
    @Test
    public void givenAmount_whenFormatterIsCN_thenAmountIsInCNFormatAmount() {
        // given - define precondition for test

        // when - perform the desiredAction
        String formattedValue = formatter.formatAmount(AMOUNT, LocaleEnum.CHN);

        // then - verify the output
        assertThat(formattedValue).isNotNull();
        assertThat(formattedValue).isEqualTo("CN¥ 2,399.99");
    }

    @DisplayName("Check formatting for ESP")
    @Test
    public void givenAmount_whenFormatterIsESP_thenAmountIsInESPFormatAmount() {
        // given - define precondition for test

        // when - perform the desiredAction
        String formattedValue = formatter.formatAmount(AMOUNT, LocaleEnum.ESP);

        // then - verify the output
        assertThat(formattedValue).isNotNull();
        assertThat(formattedValue).isEqualTo("€ 2.399,99");
    }
}