package com.example.skripsi.Activity.Koor.DaftarKurir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.skripsi.API.APIRequestData;
import com.example.skripsi.API.RetroServer;
import com.example.skripsi.Model.DataKurirModel.ResponModel;
import com.example.skripsi.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahDataKurirActivity extends AppCompatActivity {

    private String uId, uNama, uAlamat, uTelp;
    private String yId, yNama, yAlamat, yTelp;
    private EditText etId, etNama,etAlamat, etTelp;
    private Button btnUbah;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_data_kurir);

        Intent terima = getIntent();
        uId = terima.getStringExtra("xId");
        uNama = terima.getStringExtra("xNama");
        uAlamat = terima.getStringExtra("xAlamat");
        uTelp = terima.getStringExtra("xTelp");

        etId = findViewById(R.id.etId);
        etNama = findViewById(R.id.etNama);
        etAlamat = findViewById(R.id.etAlamat);
        etTelp = findViewById(R.id.etTelp);
        btnUbah = findViewById(R.id.btnUbah);

        etId.setText(uId);
        etNama.setText(uNama);
        etAlamat.setText(uAlamat);
        etTelp.setText(uTelp);

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yNama = etNama.getText().toString();
                yAlamat = etAlamat.getText().toString();
                yTelp = etTelp.getText().toString();
                updateData();
            }
        });
    }

    private void updateData(){
        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponModel> ubahData = ardData.ardUpdateData(uId, yNama, yAlamat, yTelp);

        ubahData.enqueue(new Callback<ResponModel>() {
            @Override
            public void onResponse(Call<ResponModel> call, Response<ResponModel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(UbahDataKurirActivity.this, "Kode : "+kode+" | Pesan : "
                        +pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponModel> call, Throwable t) {
                Toast.makeText(UbahDataKurirActivity.this, "Gagal Menghubungi Server! | "
                        +t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}