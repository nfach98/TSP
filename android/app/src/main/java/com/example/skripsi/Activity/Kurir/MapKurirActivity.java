package com.example.skripsi.Activity.Kurir;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.skripsi.R;
import com.mapbox.maps.MapView;

public class MapKurirActivity extends AppCompatActivity {

    MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_kurir);

        mapView = findViewById(R.id.mapView);
    }
}