package com.valentun.findgift.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.valentun.findgift.Constants;

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
    private int priceType;

    @JsonProperty("image")
    private String imageUrl;

    @JsonProperty("saved")
    private boolean isStarred;

    @JsonProperty("liked")
    private boolean isLiked;

    public static String generateImageName() {
        return "GIFT_" + System.currentTimeMillis() + Constants.Firebase.IMAGE_FORMAT;
    }

    public String getStringRating() {
        return String.valueOf(rating);
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

    public int getPriceType() {
        return priceType;
    }

    public Gift setPriceType(int priceType) {
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
