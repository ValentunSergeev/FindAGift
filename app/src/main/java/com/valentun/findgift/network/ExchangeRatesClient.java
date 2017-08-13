package com.valentun.findgift.network;

import com.valentun.findgift.models.ExchangeRates;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ExchangeRatesClient {
    @GET("latest")
    Call<ExchangeRates> getExchangeRates(@Query("base") String base);
}
