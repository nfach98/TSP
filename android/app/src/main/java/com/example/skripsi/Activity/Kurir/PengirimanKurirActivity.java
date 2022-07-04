package com.example.skripsi.Activity.Kurir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.skripsi.API.APIRequestData;
import com.example.skripsi.API.RetroServer;
import com.example.skripsi.Activity.Koor.RutePengiriman.TabelHitungActivity;
import com.example.skripsi.Adapter.AdapterPengirimanKurir;
import com.example.skripsi.Model.PengirimanModel.DataPengirimanModel;
import com.example.skripsi.Model.PengirimanModel.ResponPengirimanModel;
import com.example.skripsi.OnClickListener;
import com.example.skripsi.R;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengirimanKurirActivity extends AppCompatActivity {
    private TextView tvTotal, tvJarak;
    private RecyclerView rvPengiriman;
    private Button btnCek;

    private String idKurir;
    private final double[] latLngS = new double[]{-7.32056, 112.7099};
    private List<DataPengirimanModel> listLatLong = new ArrayList<>();
    private final List<Integer> listSubtour = new ArrayList<>();
    private final List<Double> listHaversine = new ArrayList<>();
    private double totalJarak = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengiriman_kurir);

        idKurir = getIntent().getStringExtra("id_kurir");
        tvTotal = findViewById(R.id.tvTotal);
        tvJarak = findViewById(R.id.tvJarak);
        rvPengiriman = findViewById(R.id.rvLatLong);
        btnCek = findViewById(R.id.buttonCheck);
        rvPengiriman.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        loadData();

        btnCek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!listSubtour.isEmpty() && !listHaversine.isEmpty()) {
                    Intent intent = new Intent(PengirimanKurirActivity.this, TabelHitungActivity.class);
                    Bundle args = new Bundle();
                    args.putSerializable("latlng", (Serializable) listLatLong);
                    args.putSerializable("haversine", (Serializable) listHaversine);
                    intent.putExtra("bundle", args);
                    startActivity(intent);
                }
            }
        });
    }

    private void loadData() {
        APIRequestData ardDAta = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponPengirimanModel> tampilHitung = ardDAta.ardRetrievePengirimanKurir(idKurir);

        tampilHitung.enqueue(new Callback<ResponPengirimanModel>() {
            @Override
            public void onResponse(Call<ResponPengirimanModel> call, Response<ResponPengirimanModel> response) {
                assert response.body() != null;
                List<DataPengirimanModel> dataHitungList = response.body().getData();
                DataPengirimanModel dataS = new DataPengirimanModel();
                dataS.setId(0);
                dataS.setLatitude(latLngS[0]);
                dataS.setLongitude(latLngS[1]);
                dataHitungList.add(0, dataS);

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

                listSubtour.forEach(sub -> listLatLong.add(dataHitungList.get(sub)));

                // Hitung jarak total subtour
                for (int i = 0; i < listSubtour.size() - 1; i++) {
                    double dist = listHaversine.get(coordToIndex(listSubtour.get(i), listSubtour.get(i + 1), dataHitungList.size()));
                    totalJarak += dist;
                }

                tvTotal.setText("Total pengiriman: " + (listSubtour.size() - 1));
                tvJarak.setText("Total jarak tempuh: " + totalJarak + " km");

                AdapterPengirimanKurir adCekHitung = new AdapterPengirimanKurir(PengirimanKurirActivity.this, listLatLong.subList(1, listLatLong.size()));
                rvPengiriman.setAdapter(adCekHitung);
                adCekHitung.notifyDataSetChanged();
                adCekHitung.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(Object... data) {
                    DataPengirimanModel dhm = (DataPengirimanModel) data[0];
                    Intent intent = new Intent(PengirimanKurirActivity.this, NavigasiKurirActivity.class);
                    Bundle args = new Bundle();
                    int idx = dataHitungList.indexOf(dhm);
                    if (idx == 0) {
                        args.putSerializable("from", latLngS);
                    }
                    else {
                        DataPengirimanModel origin = dataHitungList.get(idx - 1);
                        args.putSerializable("from", new double[] {origin.getLatitude(), origin.getLongitude()});
                    }
                    args.putSerializable("to", new double[] {dhm.getLatitude(), dhm.getLongitude()});
                    intent.putExtra("bundle", args);
                    startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<ResponPengirimanModel> call, Throwable t) {
                Log.d("resp", t.getMessage());
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

        for (int i = 0; i < listSubtour.size() - 1; i++) {
            for (int j = 0; j < listAvailable.size(); j++) {
                int idx1 = coordToIndex(listSubtour.get(i), listAvailable.get(j), list.size());
                int idx2 = coordToIndex(listAvailable.get(j), listSubtour.get(i + 1), list.size());
                int idx3 = coordToIndex(listSubtour.get(i), listSubtour.get(i + 1), list.size());
                double addition = listHaversine.get(idx1) + listHaversine.get(idx2) - listHaversine.get(idx3);
                if (minDistance > addition) {
                    minDistance = addition;
                    idxInsert = i;
                    idxMin = listAvailable.get(j);
                }
            }
        }

        listSubtour.add(idxInsert + 1, idxMin);
    }

    private int coordToIndex(int x, int y, int length) {
        return x + y * length;
    }

    private int[] indexToCoord(int i, int length) {
        int x = i % length;
        int y = i / ~length;
        return new int[]{x, y};
    }
}