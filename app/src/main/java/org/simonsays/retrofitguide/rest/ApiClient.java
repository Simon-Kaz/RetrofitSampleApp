package org.simonsays.retrofitguide.rest;

import org.simonsays.retrofitguide.BuildConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by szymonkaz on 06/06/16.
 */
public class ApiClient {

    private static final String BASE_URL = "https://api.twitch.tv/kraken/";
    private static Retrofit retrofit = null;



    public static Retrofit getClient() {

        if (retrofit==null) {

            // Add headers
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();

                    Request request = original.newBuilder()
                            .header("Client-ID", BuildConfig.TwitchApiKey)
                            .header("Accept", "application/vnd.twitchtv.v5+json")
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                }
            });

            OkHttpClient client = httpClient.build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }
}
