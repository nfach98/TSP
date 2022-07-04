package com.example.skripsi.Activity.Koor.DaftarKurir;

import androidx.appcompat.app.AppCompatActivity;

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

public class TambahDataKurirActivity extends AppCompatActivity {
    private EditText etId, etNama, etALamat, etTelp;
    private Button btnSimpan;
    private String id, nama, alamat, telp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data_kurir);

        etId = findViewById(R.id.etId);
        etNama = findViewById(R.id.etNama);
        etALamat = findViewById(R.id.etAlamat);
        etTelp = findViewById(R.id.etTelp);
        btnSimpan = (Button) findViewById(R.id.btnSimpan);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = etId.getText().toString();
                nama = etNama.getText().toString();
                alamat = etALamat.getText().toString();
                telp = etTelp.getText().toString();

                if (nama.trim().equals("")){
                    etNama.setError("Nama Harus Diisi!");
                }
                else if (id.trim().equals("")){
                    etId.setError("Id Kurir Harus Diisi!");
                }
                else if (alamat.trim().equals("")){
                    etALamat.setError("Alamat Harus Diisi!");
                }
                else if (telp.trim().equals("")){
                    etTelp.setError("No Telp Harus Diisi!");
                }
                else {
                    createData();
                }
            }
        });
    }

    private void  createData(){
        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponModel> simpanData = ardData.ardCreateData(id, nama, alamat, telp);

        simpanData.enqueue(new Callback<ResponModel>() {
            @Override
            public void onResponse(Call<ResponModel> call, Response<ResponModel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(TambahDataKurirActivity.this, "Kode : "+kode+" | Pesan : "
                +pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponModel> call, Throwable t) {
                Toast.makeText(TambahDataKurirActivity.this, "Gagal Menghubungi Server! | "
                +t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}