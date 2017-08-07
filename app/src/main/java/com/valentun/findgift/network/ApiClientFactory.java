package com.valentun.findgift.network;

import com.valentun.findgift.Constants;

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
                .baseUrl(Constants.URL.BASE)
                .client(buildAuthClient())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    private static OkHttpClient buildAuthClient() {
        return new OkHttpClient.Builder()
                .addInterceptor((new ApiInterceptor()))
                .build();
    }
}
