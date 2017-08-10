package com.valentun.findgift.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

public class WidgetUtils {
    public static void colorizeTextWidget(TextView btn, @ColorRes int colorRes) {
        int color = ContextCompat.getColor(btn.getContext(), colorRes);
        btn.setTextColor(Color.WHITE);
        btn.getBackground().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
    }

    public static void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
