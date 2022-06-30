package com.example.skripsi.API;

import com.google.gson.Gson;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroServer {
    private static final String baseURL = "https://skripsi.ninofachrurozy.com/";
    private static Retrofit retro;

    public static Retrofit konekRetrofit(){

        if (retro == null){
            retro = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(createOkHttpClient())
                    .build();
        }
        return retro;
    }

    private static OkHttpClient createOkHttpClient() {
        try {
            final X509TrustManager x509TrustManager = new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {}

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {}

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }
            };
            final TrustManager[] trustAllCerts = new TrustManager[] { x509TrustManager };
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            return new OkHttpClient.Builder()
                .sslSocketFactory(sslContext.getSocketFactory(), x509TrustManager)
                .hostnameVerifier((hostname, session) -> true)
                .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
