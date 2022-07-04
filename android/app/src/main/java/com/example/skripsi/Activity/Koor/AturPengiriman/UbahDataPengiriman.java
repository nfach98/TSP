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

public class UbahDataPengiriman extends AppCompatActivity {

    private int uId;
    private String uNama, uAlamat;
    private double uLat, uLong;
    private EditText etNama, etAlamat ,etLokasi;
    private Button btnUbah;

    ActivityResultLauncher<Intent> pickLokasiLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_data_pengiriman);

        Intent terima = getIntent();
        uId = terima.getIntExtra("xId", -1);
        uNama = terima.getStringExtra("xNama");
        uAlamat = terima.getStringExtra("xAlamat");
        uLat = terima.getDoubleExtra("xLat", -0.0);
        uLong = terima.getDoubleExtra("xLong", 0.0);

        pickLokasiLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == PickLokasiActivity.RESULT_CODE) {
                    Intent data = result.getData();
                    if (data != null) {
                        uLat = Double.parseDouble(data.getStringExtra("latitude"));
                        uLong = Double.parseDouble(data.getStringExtra("longitude"));
                        etLokasi.setText(data.getStringExtra("text"));
                    }
                }
            }
        );

        etNama = findViewById(R.id.etNamaPenerima);
        etAlamat = findViewById(R.id.etAlamatP);
        etLokasi = findViewById(R.id.ac_location);
        etLokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UbahDataPengiriman.this, PickLokasiActivity.class);
                intent.putExtra("latitude", String.valueOf(uLat));
                intent.putExtra("longitude", String.valueOf(uLong));
                pickLokasiLauncher.launch(intent);
            }
        });
        btnUbah = findViewById(R.id.btnUbahP);

        etNama.setText(uNama);
        etAlamat.setText(uAlamat);
        etLokasi.setText(uLat + "," + uLong);

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uNama = etNama.getText().toString();
                uAlamat = etAlamat.getText().toString();
                updateData();
            }
        });
    }

    private void updateData(){
        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponPengirimanModel> ubahData = ardData.ardUpdatePengiriman(uId, uNama, uAlamat, uLat, uLong);

        ubahData.enqueue(new Callback<ResponPengirimanModel>() {
            @Override
            public void onResponse(Call<ResponPengirimanModel> call, Response<ResponPengirimanModel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(
                    UbahDataPengiriman.this,
                    "Kode : "+kode+" | Pesan : " +pesan,
                    Toast.LENGTH_SHORT
                ).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponPengirimanModel> call, Throwable t) {
                Toast.makeText(
                    UbahDataPengiriman.this,
                    "Gagal Menghubungi Server! | " +t.getMessage(),
                    Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}