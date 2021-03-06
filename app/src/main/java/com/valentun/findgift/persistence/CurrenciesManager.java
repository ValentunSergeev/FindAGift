package com.valentun.findgift.persistence;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.valentun.findgift.R;
import com.valentun.findgift.models.ExchangeRates;
import com.valentun.findgift.models.Gift;

import java.lang.reflect.Field;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import static com.valentun.findgift.Constants.Convert.EUR;
import static com.valentun.findgift.Constants.Convert.SMALL_PRICE_THRESHOLD;
import static com.valentun.findgift.Constants.PREFERENCES.DEFAULT_PRICE_TYPE;
import static com.valentun.findgift.Constants.PREFERENCES.PRICE_TYPE_KEY;

public class CurrenciesManager {
    private static Context appContext;
    private static String[] priceValues;
    private static List<String> priceKeys;
    private static ExchangeRates PreferredCurrencyRates;
    private static ExchangeRates EURRates;

    public static void with(Context context) {
        CurrenciesManager.appContext = context.getApplicationContext();
        String[] priceKeysArr = context.getResources().getStringArray(R.array.money_types_keys);
        priceKeys = Arrays.asList(priceKeysArr);
        priceValues = context.getResources().getStringArray(R.array.money_types_values);
    }

    public static String getPreferredPriceSymbol() {
        return getPriceSymbol(getPreferredPriceType());
    }

    public static String getPriceSymbol(String priceType) {
        int index = priceKeys.indexOf(priceType);
        return priceValues[index];
    }

    public static String getPreferredPriceType() {
        return getPreferences().getString(PRICE_TYPE_KEY, DEFAULT_PRICE_TYPE);
    }

    public static String convertPriceToPreferred(Gift gift) {
        if (PreferredCurrencyRates.base.equals(EUR)) return formatPrice(gift.getDoublePrice());

        double result = gift.getDoublePrice() / PreferredCurrencyRates.rates.EUR;
        return formatPrice(result);
    }

    public static String covertPriceToEUR(Gift gift) {
        if (gift.getPriceType().equals(EURRates.base)) return formatPrice(gift.getDoublePrice());

        try {
            Field field = ExchangeRates.Rates.class.getField(gift.getPriceType());

            double coefficient = field.getDouble(EURRates.rates);
            double result = gift.getDoublePrice() / coefficient;

            return formatPrice(result);
        } catch (Exception e) {
            return formatPrice(gift.getDoublePrice());
        }
    }

    public static String convertPreferredToEUR(String input) {
        if (input == null) return null;
        double inputDouble = Double.parseDouble(input);
        double result = inputDouble * PreferredCurrencyRates.rates.EUR;
        return String.valueOf(result);
    }

    private static String formatPrice(double result) {
        DecimalFormat format;

        if (result > SMALL_PRICE_THRESHOLD) {
            format = new DecimalFormat("#");
//            if (result > MIDDLE_PRICE_THRESHOLD)
//                result -= result % MIDDLE_ROUND_COEFFICIENT;
//            if (result > BIG_PRICE_THRESHOLD)
//                result -= result % BIG_ROUND_COEFFICIENT;
        } else {
            format = new DecimalFormat("#.#");
        }

        format.setRoundingMode(RoundingMode.CEILING);

        return format.format(result);
    }

    private static SharedPreferences getPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(appContext);
    }


    public static void setPreferredCurrencyRates(ExchangeRates preferredCurrencyRates) {
        CurrenciesManager.PreferredCurrencyRates = preferredCurrencyRates;
    }

    public static void setEURRates(ExchangeRates EURRates) {
        CurrenciesManager.EURRates = EURRates;
    }

    public static boolean isEURRatePresent() {
        return EURRates != null;
    }
}
