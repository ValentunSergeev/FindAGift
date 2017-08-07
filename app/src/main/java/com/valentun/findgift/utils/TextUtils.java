package com.valentun.findgift.utils;

/**
 * Created by Valentun on 06.08.2017.
 */

public class TextUtils {
    public static boolean isEmpty(CharSequence... sequences) {
        for (CharSequence sequence : sequences) {
            if (android.text.TextUtils.isEmpty(sequence)) return true;
        }

        return false;
    }
}
