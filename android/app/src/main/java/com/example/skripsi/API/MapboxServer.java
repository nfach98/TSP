package com.example.skripsi.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapboxServer {
    private static final String baseURL = "https://api.mapbox.com/geocoding/v5/";
    private static Retrofit retro;

    public static Retrofit konekRetrofit(){

        if (retro == null){
            retro = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        }
        return retro;
    }

}
