package com.valentun.findgift.utils;

import android.content.Context;

import com.valentun.findgift.Constants;
import com.valentun.findgift.R;

/**
 * Created by Valentun on 10.08.2017.
 */

public class SearchUtils {
    public static String getGenderFromButtonId(int id) {
        switch (id) {
            case R.id.gender_male: return Constants.GIFT_PARAMS.GENDER_MALE;
            case R.id.gender_female: return Constants.GIFT_PARAMS.GENDER_FEMALE;
            case R.id.gender_any: return Constants.GIFT_PARAMS.GENDER_ANY;
            default: throw new RuntimeException("Unknown gender");
        }
    }

    public static String getEventFromSelectedPosition(Context context, int position) {
        int value = context.getResources().getIntArray(R.array.event_type_keys)[position];
        return String.valueOf(value);
    }
}
