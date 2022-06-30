package com.example.skripsi.Activity.Koor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.skripsi.Activity.Koor.AturPengirimanActivity.AturPengirimanActivity;
import com.example.skripsi.Activity.DataKurirActivity;
import com.example.skripsi.Activity.NgitungActivity.CekHitung;
import com.example.skripsi.Activity.PengirimanActivity.DataPengiriman;
import com.example.skripsi.R;

public class KoorKurir extends AppCompatActivity {

    CardView cardAturPengiriman, cardDaftarKurir, cardRutePengiriman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_koor_kurir);
        cardAturPengiriman = findViewById(R.id.cardAturPengiriman);
        cardDaftarKurir = findViewById(R.id.cardDaftarKurir);
        cardRutePengiriman = findViewById(R.id.cardRutePengiriman);

        cardAturPengiriman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(KoorKurir.this, DataPengiriman.class));
            }
        });

        cardDaftarKurir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(KoorKurir.this, DataKurirActivity.class));
            }
        });

        cardRutePengiriman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(KoorKurir.this, CekHitung.class));
            }
        });
    }
}