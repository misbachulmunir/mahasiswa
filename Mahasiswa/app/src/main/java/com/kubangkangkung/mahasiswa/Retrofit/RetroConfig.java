package com.kubangkangkung.mahasiswa.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroConfig {
    private static final String baseUrl="http://192.168.43.234/mahasiswa/";
    private static Retrofit retro;

    public static Retrofit konekRetrofit(){
        if(retro==null){
            retro=new Retrofit.Builder()
                    .baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retro;
    }
}
