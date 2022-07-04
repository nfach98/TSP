package com.example.skripsi.Activity.Koor.RutePengiriman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.skripsi.Adapter.AdapterIterasi;
import com.example.skripsi.Model.HitungModel.DataHitungModel;
import com.example.skripsi.Model.IterasiModel;
import com.example.skripsi.R;

import java.util.ArrayList;
import java.util.List;

public class TabelHitungActivity extends AppCompatActivity {

    private TableLayout mTableLayout;
    private RecyclerView rvIterasi;

    private List<DataHitungModel> listLatLong = new ArrayList<>();
    private final List<Integer> listSubtour = new ArrayList<>();
    private List<Double> listHaversine = new ArrayList<>();
    private List<IterasiModel> listIterasi = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabel_hitung);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("bundle");
        listLatLong = (ArrayList<DataHitungModel>) args.getSerializable("latlng");
        listHaversine = (ArrayList<Double>) args.getSerializable("haversine");

        mTableLayout = findViewById(R.id.table);
        rvIterasi = findViewById(R.id.rvIterasi);
        rvIterasi.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        cheapest();
        loadData();
    }

    public void cheapest() {
        double minHaversine = Double.MAX_VALUE;
        int idxHaversine = 0;
        for (int i = 1; i < listLatLong.size() - 1; i++) {
            if (listHaversine.get(i) < minHaversine) {
                minHaversine = listHaversine.get(i);
                idxHaversine = i;
            }
        }

        listSubtour.add(0);
        listSubtour.add(idxHaversine);
        for (int i = 0; i < listLatLong.size() - 2; i++) {
            insertion(listLatLong);
        }
    }

    public void loadData() {
        int len = listLatLong.size();

        for (int i = -1; i < len; i++) {
            final TableRow row = new TableRow(TabelHitungActivity.this);
            for (int j = -1; j < len; j++) {
                final TextView txt = new TextView(TabelHitungActivity.this);
                txt.setEllipsize(TextUtils.TruncateAt.END);
                if (i < 0 || j < 0) {
                    txt.setTextColor(Color.WHITE);
                    txt.setBackgroundColor(getResources().getColor(android.R.color.black));
                    txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    txt.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
                    txt.setGravity(Gravity.CENTER);
                    if (i < 0) {
                        if (j < 0) {
                            txt.setText("");
                        } else if (j == 0) {
                            txt.setText("s");
                        } else {
                            txt.setText("d" + j);
                        }
                    } else if (j < 0) {
                        if (i == 0) {
                            txt.setText("s");
                        } else {
                            txt.setText("d" + i);
                        }
                    }
                } else {
                    double value = listHaversine.get(coordToIndex(i, j, len));
                    if (value == 0.0) {
                        txt.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
                    }
                    txt.setTextColor(Color.BLACK);
                    txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    txt.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
                    txt.setGravity(Gravity.CENTER);
                    txt.setText(String.valueOf(value));
                }
                int density = (int) getResources().getDisplayMetrics().density;
                txt.setLayoutParams(new TableRow.LayoutParams(density * 80, density * 60));
                row.addView(txt);
            }
            mTableLayout.addView(row);
        }

        AdapterIterasi adapter = new AdapterIterasi(TabelHitungActivity.this, listIterasi);
        rvIterasi.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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

        IterasiModel iter = new IterasiModel();
        iter.setArc1(listSubtour.get(idxInsert));
        iter.setArc2(listSubtour.get(idxInsert + 1));
        iter.setInserted("d" + idxMin);
        listIterasi.add(iter);

        listSubtour.add(idxInsert + 1, idxMin);
    }

    private int coordToIndex(int x, int y, int length) {
        return x + y * length;
    }
}