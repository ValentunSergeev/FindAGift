package com.valentun.findgift.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.valentun.findgift.Constants;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Gift {
    private String name;
    private String description;
    private String price;
    private int rating;

    @JsonProperty("price_type")
    private String priceType;

    @JsonProperty("image")
    private String imageUrl;

    @JsonProperty("saved")
    private boolean isSaved;

    @JsonProperty("rate_type")
    private String rateState;

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void setSaved(boolean saved) {
        isSaved = saved;
    }

    public String getRateState() {
        return rateState;
    }

    public void setRateState(String rateState) {
        this.rateState = rateState;
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

    public static String generateImageName() {
        return "GIFT_" + System.currentTimeMillis() + Constants.Firebase.IMAGE_FORMAT;
    }
}
