package com.example.skripsi.Activity.Kurir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.skripsi.API.APIRequestData;
import com.example.skripsi.API.RetroServer;
import com.example.skripsi.Adapter.AdapterAturKurir;
import com.example.skripsi.Model.DataKurirModel.ResponModel;
import com.example.skripsi.Model.PengirimanModel.DataPengirimanModel;
import com.example.skripsi.Model.PengirimanModel.ResponPengirimanModel;
import com.example.skripsi.OnClickListener;
import com.example.skripsi.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AturPengirimanKurirActivity extends AppCompatActivity {

    private RecyclerView rvPengiriman;
    private AdapterAturKurir adPengiriman;
    private RecyclerView.LayoutManager lmPengiriman;
    private List<DataPengirimanModel> listPengiriman = new ArrayList<>();
    private List<Integer> listSelected = new ArrayList<>();
    private Button btnSubmit;
    private String idKurir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atur_pengiriman_kurir);

        idKurir = getIntent().getStringExtra("id_kurir");
        rvPengiriman = findViewById(R.id.rv_pengiriman);
        lmPengiriman = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvPengiriman.setLayoutManager(lmPengiriman);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strList = listSelected.toString().replace(" ", "");
                updateList(strList.substring(1, strList.length() - 1));
            }
        });
        retrievePengiriman();
    }

    public void retrievePengiriman() {
        APIRequestData ardDAta = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponPengirimanModel> tampilPengiriman = ardDAta.ardretriveAvailablePengiriman(idKurir);

        tampilPengiriman.enqueue(new Callback<ResponPengirimanModel>() {
            @Override
            public void onResponse(Call<ResponPengirimanModel> call, Response<ResponPengirimanModel> response) {
                listPengiriman = response.body().getData();
                listPengiriman.forEach(data -> {
                    if (data.getIsSelected()) listSelected.add(data.getId());
                });
                adPengiriman = new AdapterAturKurir(
                    AturPengirimanKurirActivity.this,
                    listPengiriman,
                    listSelected
                );
                adPengiriman.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(Object... data) {
                        int index = Integer.parseInt(data[0].toString());
                        ImageView ivCheck = (ImageView) data[1];
                        if (listSelected.contains(index)) {
                            listSelected.remove((Integer) index);
                            ivCheck.setVisibility(View.GONE);
                        } else {
                            listSelected.add(index);
                            ivCheck.setVisibility(View.VISIBLE);
                        }
                    }
                });
                rvPengiriman.setAdapter(adPengiriman);
                adPengiriman.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponPengirimanModel> call, Throwable t) {
                Toast.makeText(AturPengirimanKurirActivity.this, "Gagal menghubungi server!"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateList(String ids){
        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponModel> callUpdateList = ardData.ardUpdateListPengirimanKurir(idKurir, ids);

        callUpdateList.enqueue(new Callback<ResponModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponModel> call, @NonNull Response<ResponModel> response) {
                if (response.body() != null) {
                    int kode = response.body().getKode();
                    if (kode == 1) {
                        Toast.makeText(
                            AturPengirimanKurirActivity.this,
                            "Data pengiriman kurir berhasil tersimpan",
                            Toast.LENGTH_SHORT
                        ).show();
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponModel> call, @NonNull Throwable t) {
                Toast.makeText(
                    AturPengirimanKurirActivity.this,
                    t.getMessage(),
                    Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}
