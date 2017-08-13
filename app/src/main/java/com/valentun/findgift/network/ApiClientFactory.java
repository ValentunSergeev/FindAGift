package com.valentun.findgift.network;

import com.valentun.findgift.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ApiClientFactory {
    private static Retrofit retrofit;
    private static APIClient apiClient;

    public static APIClient getApiClient() {
        if(retrofit == null) initRetrofit();
        if (apiClient == null) apiClient = retrofit.create(APIClient.class);
        return apiClient;
    }

    private static void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL.API_BASE)
                .client(buildAuthClient())
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
