package com.valentun.findgift;

/**
 * Created by Valentun on 31.07.2017.
 */

public class Constants {
    public static class Firebase {
        public static final String IMAGE_STORAGE_KEY = "images";
        public static final String IMAGE_FORMAT = ".jpg";
    }

    public static class URL {
        public static final String BASE = "https://findagift.herokuapp.com/";
    }

    public static class PREFERENCES {
        public static final String APP_KEY = "findagift";
        public static final int DEFAULT_INT_VALUE = -1;
        public static final String DEFAULT_STRING_VALUE = "null";
    }

    public static class SEARCH_PARAMS {
        public static final String GENDER_ANY = "01";
        public static final String GENDER_MALE = "0";
        public static final String GENDER_FEMALE = "1";
    }

    public static final int FAB_SCROLL_THRESHOLD = 5;

    public static final String ACCESS_TOKEN_KEY = "Access-Token";
    public static final String CLIENT_KEY = "Client";
    public static final String UID_KEY = "Uid";

    public static final int IMAGE_WIDTH_PX = 854;
    public static final int IMAGE_HEIGHT_PX = 480;

    public static final String[] PERMISSIONS = {android.Manifest.permission.READ_EXTERNAL_STORAGE};
}
