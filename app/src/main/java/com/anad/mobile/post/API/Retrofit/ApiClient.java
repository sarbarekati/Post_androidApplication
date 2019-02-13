package com.anad.mobile.post.API.Retrofit;

import android.content.Context;

import com.anad.mobile.post.BuildConfig;
import com.anad.mobile.post.Storage.PostSharedPreferences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_URL = BuildConfig.BASE_URL;
    private static Retrofit retrofit = null;
    private static PostSharedPreferences preferences;

    public static Retrofit getInstance(final Context context) {




        if (retrofit == null) {

            preferences = new PostSharedPreferences(context);


            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response originalResponse = chain.proceed(chain.request());

                    if (!originalResponse.headers("Set-Cookie").isEmpty()) {
                        HashSet<String> cookies = new HashSet<>();

                        for (String header : originalResponse.headers("Set-Cookie")) {
                            cookies.add(header);
                        }

                        preferences.setCookie(buildCookie(cookies));
                    }
                    return originalResponse;
                }
            });

            builder.readTimeout(40, TimeUnit.SECONDS);
            builder.connectTimeout(40, TimeUnit.SECONDS);

            OkHttpClient client = builder.build();


            Gson gson = new GsonBuilder().setLenient().create();



            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

        }
        return retrofit;
    }


    private static String buildCookie(HashSet<String> cookies) {
        StringBuilder builder = new StringBuilder();
        for (String cookie : cookies) {
            builder.append(cookie);
            builder.append(";");
        }
        return builder.toString() + ";";
    }





}
