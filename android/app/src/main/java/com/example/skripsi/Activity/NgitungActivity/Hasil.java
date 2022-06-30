package com.example.skripsi.Activity.NgitungActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.skripsi.R;

public class Hasil extends AppCompatActivity {

    private int id;
    private Double lati, longi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil);
    }

    /*public void ambilLatLong(){
        APIRequestData ardDAta = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponPengirimanModel> LatLong = ardDAta.ardAmbilLatLong();

        LatLong.enqueue(new Callback<ResponPengirimanModel>() {
            @Override
            public void onResponse(Call<ResponPengirimanModel> call, Response<ResponPengirimanModel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(Hasil.this, "Kode : "+kode+" | Pesan : "+pesan, Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(Call<ResponPengirimanModel> call, Throwable t) {
                Toast.makeText(Hasil.this, "Gagal menghubungi server!"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }*/

}