package com.example.skripsi.Activity.Koor.RutePengiriman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.skripsi.API.APIRequestData;
import com.example.skripsi.API.RetroServer;
import com.example.skripsi.Adapter.AdapterHitung;
import com.example.skripsi.Model.HitungModel.DataHitungModel;
import com.example.skripsi.Model.HitungModel.ResponHitungModel;
import com.example.skripsi.R;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CekHitungActivity extends AppCompatActivity {
    private RecyclerView rvCekHitung;
    private RecyclerView.Adapter adCekHitung;
    private RecyclerView.LayoutManager lmCekHitung;
    private List<DataHitungModel> listLatLong = new ArrayList<>();
    private Button btnCek;
    private final List<Integer> listSubtour = new ArrayList<>();

    private final double[] latLngS = new double[] {-7.32056, 112.7099};
    private final List<Double> listHaversine = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cek_hitung);
        rvCekHitung = findViewById(R.id.rvLatLong);
        btnCek = findViewById(R.id.buttonCheck);
        lmCekHitung = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvCekHitung.setLayoutManager(lmCekHitung);
        retrieveCekHitung();

        btnCek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CekHitungActivity.this, TabelHitungActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("latlng", (Serializable) listLatLong);
                args.putSerializable("haversine", (Serializable) listHaversine);
                intent.putExtra("bundle", args);
                startActivity(intent);
            }
        });

    }

    public void retrieveCekHitung() {
        APIRequestData ardDAta = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponHitungModel> tampilHitung = ardDAta.ardAmbilLatLong();

        tampilHitung.enqueue(new Callback<ResponHitungModel>() {
            @Override
            public void onResponse(Call<ResponHitungModel> call, Response<ResponHitungModel> response) {
                List<DataHitungModel> dataHitungList = new ArrayList<>();

                DataHitungModel dataS = new DataHitungModel();
                dataS.setId(0);
                dataS.setLatitude(latLngS[0]);
                dataS.setLongitude(latLngS[1]);

                dataHitungList.add(dataS);
                dataHitungList.addAll(response.body().getData());
                dataHitungList.forEach((dat -> {
                    dataHitungList.forEach((dat2 -> {
                        Double dist = haversine(dat.getLatitude(), dat.getLongitude(), dat2.getLatitude(), dat2.getLongitude());
                        listHaversine.add(dist);
                    }));
                }));

                double minHaversine = Double.MAX_VALUE;
                int idxHaversine = 0;
                for (int i = 1; i < dataHitungList.size() - 1; i++) {
                    if (listHaversine.get(i) < minHaversine) {
                        minHaversine = listHaversine.get(i);
                        idxHaversine = i;
                    }
                }

                listSubtour.add(0);
                listSubtour.add(idxHaversine);
                for (int i = 0; i < dataHitungList.size() - 2; i++) {
                    insertion(dataHitungList);
                }

                listSubtour.forEach(sub -> {
                    listLatLong.add(dataHitungList.get(sub));
                });
                adCekHitung = new AdapterHitung(CekHitungActivity.this, listLatLong.subList(1, listLatLong.size()));
                rvCekHitung.setAdapter(adCekHitung);
                adCekHitung.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponHitungModel> call, Throwable t) {
                Toast.makeText(CekHitungActivity.this, "Gagal menghubungi server!"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private double toRad(Double value) {
        return value * Math.PI / 180;
    }

    private double haversine(double lat1, double lng1, double lat2, double lng2) {
        final int R = 6371; // in Km
        double latDistance = toRad(lat2 - lat1);
        double lonDistance = toRad(lng2 - lng1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
            Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) *
            Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;
        BigDecimal bd = new BigDecimal(distance).setScale(4, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private void insertion(List list) {
        List<Integer> listAvailable = new ArrayList<>();
        double minDistance = Double.MAX_VALUE;
        int idxInsert = 0;
        int idxMin = 0;

        for (int i = 0; i < list.size(); i++) {
            if (!listSubtour.contains(i)) {
                listAvailable.add(i);
            }
        }

        for (int i = 0; i < listSubtour.size()-1; i++) {
            for (int j = 0; j < listAvailable.size(); j++) {
                int idx1 = coordToIndex(listSubtour.get(i), listAvailable.get(j), list.size());
                int idx2 = coordToIndex(listAvailable.get(j), listSubtour.get(i+1), list.size());
                int idx3 = coordToIndex(listSubtour.get(i), listSubtour.get(i+1), list.size());
                double addition = listHaversine.get(idx1) + listHaversine.get(idx2) - listHaversine.get(idx3);
                if (minDistance > addition) {
                    minDistance = addition;
                    idxInsert = i;
                    idxMin = listAvailable.get(j);
                }
            }
        }

        listSubtour.add(idxInsert+1, idxMin);
    }

    private int coordToIndex(int x, int y, int length) {
        return x + y * length;
    }

    private int[] indexToCoord(int i, int length) {
        int x = i % length;
        int y = i /~ length;
        return new int[]{x, y};
    }
}