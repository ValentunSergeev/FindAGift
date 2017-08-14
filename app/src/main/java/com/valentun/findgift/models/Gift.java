package com.valentun.findgift.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.DecimalFormat;

import static com.valentun.findgift.Constants.Firebase;
import static com.valentun.findgift.Constants.GIFT_PARAMS.MILLION_POSTIFX;
import static com.valentun.findgift.Constants.GIFT_PARAMS.THOUSAND_POSTFIX;
import static com.valentun.findgift.Constants.ONE_MILLION;
import static com.valentun.findgift.Constants.ONE_THOUSAND;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Gift {
    private long id;
    private String name;
    private String description;
    private String price;
    private int rating;
    private String gender;

    @JsonProperty("min_age")
    private int minAge;

    @JsonProperty("max_age")
    private int maxAge;

    @JsonProperty("event_type")
    private int eventType;

    @JsonProperty("price_type")
    private String priceType;

    @JsonProperty("image")
    private String imageUrl;

    @JsonProperty("saved")
    private boolean isStarred;

    @JsonProperty("liked")
    private boolean isLiked;

    @JsonIgnore
    private static final DecimalFormat rateFormat = new DecimalFormat("#.#");

    public static String generateImageName() {
        return "GIFT_" + System.currentTimeMillis() + Firebase.IMAGE_FORMAT;
    }

    public String getStringRating() {
        String result = "";
        if (rating == 0) return result;
        if (rating >= ONE_THOUSAND) {
            if (rating >= ONE_MILLION) {
                return rateFormat.format(rating / ONE_MILLION) + MILLION_POSTIFX;
            }
            if (rating >= 100 * ONE_THOUSAND) {
                return rateFormat.format((int)(rating / ONE_THOUSAND)) + THOUSAND_POSTFIX;
            }
            return rateFormat.format(rating / ONE_THOUSAND) + THOUSAND_POSTFIX;
        }
        return String.valueOf(rating);
    }

    public double getDoublePrice() {
        return Double.parseDouble(price);
    }

    public void updateLikeState(Gift gift) {
        isLiked = gift.isLiked();
        rating = gift.getRating();
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isStarred() {
        return isStarred;
    }

    public void setStarred(boolean starred) {
        isStarred = starred;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setIsLiked(boolean isLiked) {
        this.isLiked = isLiked;
    }

    public String getName() {
        return name;
    }

    public Gift setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Gift setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public Gift setPrice(String price) {
        this.price = price;
        return this;
    }

    public String getPriceType() {
        return priceType;
    }

    public Gift setPriceType(String priceType) {
        this.priceType = priceType;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Gift setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public Gift setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public int getMinAge() {
        return minAge;
    }

    public Gift setMinAge(int minAge) {
        this.minAge = minAge;
        return this;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public Gift setMaxAge(int maxAge) {
        this.maxAge = maxAge;
        return this;
    }

    public int getEventType() {
        return eventType;
    }

    public Gift setEventType(int eventType) {
        this.eventType = eventType;
        return this;
    }
}
