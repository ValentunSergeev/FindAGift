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
        public static final String API_BASE = "https://findagift.herokuapp.com/";
        public static final String RATES_BASE = "http://api.fixer.io/";
    }

    public static class PREFERENCES {
        public static final String APP_KEY = "findagift";
        public static final String PRICE_TYPE_KEY = "price_type";

        public static final int DEFAULT_INT_VALUE = -1;
        public static final String DEFAULT_PRICE_TYPE = "USD";
        public static final String DEFAULT_STRING_VALUE = "null";
    }

    public static class GIFT_PARAMS {
        public static final String GENDER_ANY = "01";
        public static final String GENDER_MALE = "0";
        public static final String GENDER_FEMALE = "1";

        public static final int MIN_AGE_VALUE = 0;
        public static final int MAX_AGE_VALUE = 150;

        public static final String THOUSAND_POSTFIX = "K";
        public static final String MILLION_POSTIFX = "M";
    }

    public static class Convert {
        public static final int SMALL_PRICE_THRESHOLD = 10;
//        public static final int MIDDLE_PRICE_THRESHOLD = 100;
//        public static final int MIDDLE_ROUND_COEFFICIENT = 10;
//        public static final int BIG_PRICE_THRESHOLD = 1000;
//        public static final int BIG_ROUND_COEFFICIENT = 100;
        public static final String EUR = "EUR";
    }


    public static final int FAB_SCROLL_THRESHOLD = 5;

    public static final String ACCESS_TOKEN_KEY = "Access-Token";
    public static final String CLIENT_KEY = "Client";
    public static final String UID_KEY = "Uid";

    public static final int IMAGE_WIDTH_PX = 352;
    public static final int IMAGE_HEIGHT_PX = 240;

    public static final float ONE_THOUSAND = 1000f;
    public static final float ONE_MILLION = 1000000f;

    public static final String[] PERMISSIONS = {android.Manifest.permission.READ_EXTERNAL_STORAGE};
}
