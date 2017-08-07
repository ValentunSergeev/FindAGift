package com.valentun.findgift.network;

import android.support.annotation.NonNull;

import com.valentun.findgift.persistence.SessionManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Valentun on 06.08.2017.
 */

public class ApiInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();

        Request.Builder requestBuilder = originalRequest.newBuilder()
                .addHeader("Content-Type", "application/json")
                .method(originalRequest.method(), originalRequest.body());

        if(!SessionManager.isSessionStarted()) {
            return chain.proceed(requestBuilder.build());
        }

        HashMap<String, String> headers = SessionManager.getAuthHeaders();

        for (Map.Entry<String, String> entry: headers.entrySet()){
            requestBuilder.addHeader(entry.getKey(), entry.getValue());
        }

        return chain.proceed(requestBuilder.build());
    }


}
