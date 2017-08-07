package com.valentun.findgift.utils;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

public class WidgetUtils {
    public static void colorizeTextWidget(TextView btn, @ColorRes int colorRes) {
        int color = ContextCompat.getColor(btn.getContext(), colorRes);
        btn.setTextColor(Color.WHITE);
        btn.getBackground().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
    }
}
