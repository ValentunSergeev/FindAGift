package com.valentun.findgift.network;

import com.valentun.findgift.models.AuthModel;
import com.valentun.findgift.models.Gift;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIClient {
    @POST("auth/")
    Call<ResponseBody> createUser(@Body AuthModel authModel);

    @POST("auth/sign_in")
    Call<ResponseBody> createSession(@Body AuthModel authModel);

    @GET("gifts.json")
    Call<List<Gift>> getGifts();

    @POST("gifts.json")
    Call<ResponseBody> createGift(@Body Gift gift);
}
