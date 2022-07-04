package com.example.skripsi.Activity.Koor.AturPengiriman;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.skripsi.API.APIRequestData;
import com.example.skripsi.API.RetroServer;
import com.example.skripsi.Model.PengirimanModel.ResponPengirimanModel;
import com.example.skripsi.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahDataPengiriman extends AppCompatActivity{

    EditText etNamaP, etAlmatP, etLocation;
    Button btnSimpan;

    String nama, alamat;
    String latitude, longitude = "";

    ActivityResultLauncher<Intent> pickLokasiLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data_pengiriman);

        pickLokasiLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == PickLokasiActivity.RESULT_CODE) {
                    Intent data = result.getData();
                    if (data != null) {
                        latitude = data.getStringExtra("latitude");
                        longitude = data.getStringExtra("longitude");
                        etAlmatP.setText(data.getStringExtra("text"));
                        etLocation.setText(latitude + "," + longitude);
                    }
                }
            }
        );

        etAlmatP = findViewById(R.id.etAlamatP);
        etNamaP = findViewById(R.id.etNamaPenerima);

        etLocation = findViewById(R.id.ac_location);
        etLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TambahDataPengiriman.this, PickLokasiActivity.class);
                if (latitude != null && longitude != null) {
                    intent.putExtra("latitude", latitude);
                    intent.putExtra("longitude", longitude);
                }
                pickLokasiLauncher.launch(intent);
            }
        });

        btnSimpan = findViewById(R.id.btnSimpanP);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = etNamaP.getText().toString();
                alamat = etAlmatP.getText().toString();

                if (nama.trim().equals("")){
                    etNamaP.setError("Nama Harus Diisi!");
                }
                else if (alamat.trim().equals("")){
                    etAlmatP.setError("Alamat Harus Diisi!");
                }
                else if (latitude.equals("") || longitude.equals("")){
                    etLocation.setError("Lokasi Harus Diisi!");
                }
                else {
                    createData();
                }
            }
        });
    }

    private void createData(){
        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponPengirimanModel> simpanData = ardData.ardCreatePengiriman(nama, alamat, latitude, longitude);

        simpanData.enqueue(new Callback<ResponPengirimanModel>() {
            @Override
            public void onResponse(Call<ResponPengirimanModel> call, Response<ResponPengirimanModel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();
                Toast.makeText(
                    TambahDataPengiriman.this,
                    "Kode : "+kode+" | Pesan : " + pesan,
                    Toast.LENGTH_SHORT
                ).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponPengirimanModel> call, Throwable t) {
                Toast.makeText(
                    TambahDataPengiriman.this,
                    "Gagal Menghubungi Server! | " + t.getMessage(),
                    Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}