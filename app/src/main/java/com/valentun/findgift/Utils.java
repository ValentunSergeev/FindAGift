package com.valentun.findgift;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;

/**
 * Created by Valentun on 31.07.2017.
 */

public class Utils {
    public static Bitmap getImage(Bitmap bitmap , Context context, int size) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return ThumbnailUtils.extractThumbnail(bitmap, (int) (size * scale), (int) (size * scale));
    }
}
