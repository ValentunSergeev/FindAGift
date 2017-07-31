package com.valentun.findgift;

/**
 * Created by Vlad on 30.07.2017.
 */

public class Gift {
    public String name;
    public String price;
    public String imageURL;

    public Gift() {
    }

    public Gift(String name, String price, String imageURL) {
        this.name = name;
        this.imageURL = imageURL;
        this.price = price;
    }

    public static String generateId(Gift gift) {
        return System.currentTimeMillis() + "_" + gift.name;
    }
}
