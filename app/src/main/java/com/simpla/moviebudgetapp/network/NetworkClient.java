package com.simpla.moviebudgetapp.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {

    public static Retrofit retrofit;

    public NetworkClient(){
    }

    public static Retrofit getRetrofit(){

        if(retrofit==null){
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            OkHttpClient okHttpClient = builder.build();
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/3/") //api address
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }

        return retrofit;
    }
}
