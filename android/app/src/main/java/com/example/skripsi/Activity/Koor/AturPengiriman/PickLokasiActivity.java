package com.example.skripsi.Activity.Koor.AturPengiriman;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.skripsi.API.APIRequestData;
import com.example.skripsi.API.MapboxServer;
import com.example.skripsi.R;
import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.CameraState;
import com.mapbox.maps.MapView;
import com.mapbox.maps.MapboxMap;
import com.mapbox.maps.extension.observable.eventdata.CameraChangedEventData;
import com.mapbox.maps.plugin.delegates.listeners.OnCameraChangeListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PickLokasiActivity extends AppCompatActivity {

    static final int RESULT_CODE = 111;

    private MapView mapView;
    private Button btnPick;
    private ImageView dropPinView;
    private LinearLayout layoutLokasi;
    private TextView tvLokasi, tvAlamat;

    private MapboxMap mapboxMap;

    private double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_lokasi);

        Intent data = getIntent();

        mapView = findViewById(R.id.mapView);
        mapboxMap = mapView.getMapboxMap();
        btnPick = findViewById(R.id.btnPick);
        dropPinView = new ImageView(this);
        dropPinView.setImageResource(R.drawable.ic_baseline_location_on_24);
        layoutLokasi = findViewById(R.id.layoutLokasi);
        tvLokasi = findViewById(R.id.tvLokasi);
        tvAlamat = findViewById(R.id.tvAlamat);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        float density = getResources().getDisplayMetrics().density;
        params.bottomMargin = (int) (12 * density);
        params.height = (int) (40 * density);
        params.width = (int) (40 * density);
        dropPinView.setLayoutParams(params);
        mapView.addView(dropPinView);

        if (data != null && data.getStringExtra("latitude") != null
                && data.getStringExtra("longitude") != null) {
            double lat = Double.parseDouble(data.getStringExtra("latitude"));
            double lng = Double.parseDouble(data.getStringExtra("longitude"));

            CameraOptions cam = new CameraOptions.Builder()
                    .center(Point.fromLngLat(lng, lat))
                    .build();

            mapboxMap.setCamera(cam);
        }
        mapboxMap.addOnCameraChangeListener(new OnCameraChangeListener() {
            @Override
            public void onCameraChanged(@NonNull CameraChangedEventData cameraChangedEventData) {
                CameraState cameraState = mapboxMap.getCameraState();
                lat = cameraState.getCenter().latitude();
                lng = cameraState.getCenter().longitude();

                APIRequestData searchData = MapboxServer.konekRetrofit().create(APIRequestData.class);
                Call<ResponseBody> searchCall = searchData.reverseLocation(
                    String.valueOf(lng),
                    String.valueOf(lat),
                    "address,place,neighborhood,poi,locality,region",
                    1,
                    getString(R.string.mapbox_access_token)
                );

                searchCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            if (response.body() != null) {
                                String json = response.body().string();
                                JSONObject jsonObject = new JSONObject(json);
                                JSONArray array = jsonObject.getJSONArray("features");
                                List<JSONObject> list = new ArrayList<>();

                                for (int i = 0; i < array.length(); i++) {
                                    list.add(array.getJSONObject(i));
                                }

                                tvLokasi.setText(list.get(0).getString("text"));
                                tvAlamat.setText(list.get(0).getJSONObject("properties").getString("address"));
                                layoutLokasi.setVisibility(View.VISIBLE);
                            } else {
                                layoutLokasi.setVisibility(View.GONE);
                            }
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d("search", t.getMessage());
                    }
                });
            }
        });

        btnPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("latitude", String.valueOf(lat));
                intent.putExtra("longitude", String.valueOf(lng));
                intent.putExtra("text", tvLokasi.getText());
                setResult(RESULT_CODE, intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}