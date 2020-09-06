package com.ensim.mic.slink.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {

    public static final String API_URL = "http://212.227.200.43/api/";
    static private RetrofitFactory INSTANCE =new RetrofitFactory();

    private Retrofit retrofit;

    private RetrofitFactory() {
        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RetrofitFactory getINSTANCE() {
        return INSTANCE;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
