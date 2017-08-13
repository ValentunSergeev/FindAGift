package com.valentun.findgift.network;

import com.valentun.findgift.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitClientFactory {
    private static APIClient apiClient;
    private static ExchangeRatesClient exchangeRatesClient;

    public static APIClient getApiClient() {
        if (apiClient == null) apiClient = getAPIRetrofit().create(APIClient.class);
        return apiClient;
    }

    public static ExchangeRatesClient getExchangeRatesClient() {
        if (exchangeRatesClient == null) exchangeRatesClient = getRatesRetrofit()
                .create(ExchangeRatesClient.class);
        return exchangeRatesClient;
    }

    private static Retrofit getAPIRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Constants.URL.API_BASE)
                .client(buildAuthClient())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    private static Retrofit getRatesRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Constants.URL.RATES_BASE)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    private static OkHttpClient buildAuthClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor((new ApiInterceptor()))
                .build();
    }
}
