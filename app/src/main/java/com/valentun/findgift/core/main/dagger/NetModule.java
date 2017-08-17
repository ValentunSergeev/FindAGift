package com.valentun.findgift.core.main.dagger;

import com.valentun.findgift.Constants;
import com.valentun.findgift.network.APIClient;
import com.valentun.findgift.network.ApiInterceptor;
import com.valentun.findgift.network.ExchangeRatesClient;


import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


@Module
public class NetModule {

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor((new ApiInterceptor()))
                .build();
    }

    @Provides @Named("api") @Singleton
    public Retrofit provideAuthRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(Constants.URL.API_BASE)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    @Provides @Named("rates") @Singleton
    public Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Constants.URL.RATES_BASE)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public APIClient provideAPIClient(@Named("api") Retrofit retrofit) {
        return retrofit.create(APIClient.class);
    }

    @Provides
    @Singleton
    public ExchangeRatesClient providesRatesClient(@Named("rates") Retrofit retrofit) {
        return retrofit.create(ExchangeRatesClient.class);
    }
}
