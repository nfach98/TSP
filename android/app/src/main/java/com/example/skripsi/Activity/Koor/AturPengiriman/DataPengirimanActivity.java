package com.example.skripsi.Activity.Koor.AturPengiriman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skripsi.API.APIRequestData;
import com.example.skripsi.API.RetroServer;
import com.example.skripsi.Adapter.AdapterPengiriman;
import com.example.skripsi.Model.PengirimanModel.DataPengirimanModel;
import com.example.skripsi.Model.PengirimanModel.ResponPengirimanModel;
import com.example.skripsi.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataPengirimanActivity extends AppCompatActivity {
    private RecyclerView rvPengiriman;
    private RecyclerView.Adapter adPengiriman;
    private RecyclerView.LayoutManager lmPengiriman;
    private List<DataPengirimanModel> listPengiriman = new ArrayList<>();
    private FloatingActionButton fabTambah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_pengiriman);

        rvPengiriman = findViewById(R.id.rv_pengiriman);
        fabTambah = findViewById(R.id.fab_tambahKirim);
        lmPengiriman = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        rvPengiriman.setLayoutManager(lmPengiriman);
        retrievePengiriman();

        fabTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DataPengirimanActivity.this, TambahDataPengiriman.class));
            }
        });
    }

    public void retrievePengiriman(){
        APIRequestData ardDAta = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponPengirimanModel> tampilPengiriman = ardDAta.ardRetrievePengiriman();

        tampilPengiriman.enqueue(new Callback<ResponPengirimanModel>() {
            @Override
            public void onResponse(Call<ResponPengirimanModel> call, Response<ResponPengirimanModel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(DataPengirimanActivity.this, "Kode : "+kode+" | Pesan : "+pesan, Toast.LENGTH_SHORT).show();

                listPengiriman = response.body().getData();

                adPengiriman = new AdapterPengiriman(DataPengirimanActivity.this, listPengiriman);
                rvPengiriman.setAdapter(adPengiriman);
                adPengiriman.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<ResponPengirimanModel> call, Throwable t) {
                Toast.makeText(DataPengirimanActivity.this, "Gagal menghubungi server!"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}