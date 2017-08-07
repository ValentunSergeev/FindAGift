package com.valentun.findgift.utils;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;

import com.valentun.findgift.Constants;

public class BitmapUtils {
    public static Bitmap cropImage(Bitmap bitmap) {
        return ThumbnailUtils.extractThumbnail(bitmap, Constants.IMAGE_WIDTH_PX, Constants.IMAGE_HEIGHT_PX);
    }
}
