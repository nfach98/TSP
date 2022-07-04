package com.example.skripsi.Activity.Kurir;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.skripsi.R;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.api.directions.v5.models.RouteOptions;
import com.mapbox.geojson.Point;
import com.mapbox.navigation.base.ExperimentalPreviewMapboxNavigationAPI;
import com.mapbox.navigation.base.TimeFormat;
import com.mapbox.navigation.base.extensions.RouteOptionsExtensions;
import com.mapbox.navigation.base.formatter.DistanceFormatterOptions;
import com.mapbox.navigation.base.options.NavigationOptions;
import com.mapbox.navigation.base.route.RouterCallback;
import com.mapbox.navigation.base.route.RouterFailure;
import com.mapbox.navigation.base.route.RouterOrigin;
import com.mapbox.navigation.core.MapboxNavigation;
import com.mapbox.navigation.core.MapboxNavigationProvider;
import com.mapbox.navigation.core.formatter.MapboxDistanceFormatter;
import com.mapbox.navigation.core.trip.session.RouteProgressObserver;
import com.mapbox.navigation.dropin.NavigationView;
import com.mapbox.navigation.ui.maneuver.api.MapboxManeuverApi;
import com.mapbox.navigation.ui.maneuver.view.MapboxManeuverView;
import com.mapbox.navigation.ui.tripprogress.api.MapboxTripProgressApi;
import com.mapbox.navigation.ui.tripprogress.model.DistanceRemainingFormatter;
import com.mapbox.navigation.ui.tripprogress.model.EstimatedTimeToArrivalFormatter;
import com.mapbox.navigation.ui.tripprogress.model.TripProgressUpdateFormatter;
import com.mapbox.navigation.ui.tripprogress.view.MapboxTripProgressView;

import java.util.ArrayList;
import java.util.List;

@OptIn(markerClass = ExperimentalPreviewMapboxNavigationAPI.class)
public class NavigasiKurirActivity extends AppCompatActivity {
    private NavigationView navigationView;
    private MapboxManeuverView maneuverView;
    private MapboxTripProgressView tripProgressView;

    private double[] latLngFrom, latLngTo;
    MapboxNavigation mapboxNavigation;
    MapboxManeuverApi maneuverApi;
    MapboxTripProgressApi tripProgressApi;

    LocationManager lm;
    boolean gpsEnabled = false;
    boolean networkEnabled = false;

    private final RouteProgressObserver routeProgressObserver = routeProgress -> {
        maneuverView.renderManeuvers(maneuverApi.getManeuvers(routeProgress));
        tripProgressView.render(tripProgressApi.getTripProgress(routeProgress));
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigasi_kurir);
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try {
            gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ignored) {

        }

        if(!gpsEnabled && !networkEnabled) {
            new AlertDialog.Builder(this)
                .setMessage("Lokasi tidak aktif")
                .setPositiveButton("Aktifkan Lokasi", (paramDialogInterface, paramInt) -> {
                    finish();
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                })
                .setNegativeButton("Kembali", (paramDialogInterface, paramInt) -> finish())
                .show();
        }

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("bundle");
        latLngFrom = (double[]) args.getSerializable("from");
        latLngTo = (double[]) args.getSerializable("to");

        if (MapboxNavigationProvider.isCreated()) {
            mapboxNavigation = MapboxNavigationProvider.retrieve();
        } else {
            mapboxNavigation = MapboxNavigationProvider.create(
                new NavigationOptions.Builder(getApplicationContext())
                    .accessToken(getString(R.string.mapbox_access_token))
                    .build());
        }


        maneuverApi = new MapboxManeuverApi(new MapboxDistanceFormatter(
            new DistanceFormatterOptions.Builder(this).build())
        );
        tripProgressApi = new MapboxTripProgressApi(new TripProgressUpdateFormatter.Builder(this)
                .distanceRemainingFormatter(new DistanceRemainingFormatter(new DistanceFormatterOptions.Builder(this).build()))
                .estimatedTimeToArrivalFormatter(new EstimatedTimeToArrivalFormatter(this, TimeFormat.TWENTY_FOUR_HOURS))
                .build()
        );

        navigationView = findViewById(R.id.navigationView);
        maneuverView = findViewById(R.id.maneuverView);
        tripProgressView = findViewById(R.id.tripProgressView);

        navigationView.setReplayEnabled(true);

        List<Point> listCoordinates = new ArrayList<>();
        listCoordinates.add(Point.fromLngLat(latLngFrom[1], latLngFrom[0]));
        listCoordinates.add(Point.fromLngLat(latLngTo[1], latLngTo[0]));

        RouteOptions.Builder builder = RouteOptions.builder();
        RouteOptionsExtensions.applyDefaultNavigationOptions(builder);
        RouteOptionsExtensions.applyLanguageAndVoiceUnitOptions(builder, this);

        mapboxNavigation.requestRoutes(
            builder.coordinatesList(listCoordinates).build(),
            new RouterCallback() {
                @Override
                public void onRoutesReady(@NonNull List<? extends DirectionsRoute> list, @NonNull RouterOrigin routerOrigin) {
                    if (ActivityCompat.checkSelfPermission(NavigasiKurirActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(NavigasiKurirActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    mapboxNavigation.setRoutes(list);
                    mapboxNavigation.registerRouteProgressObserver(routeProgressObserver);
                    mapboxNavigation.startTripSession();
                }

                @Override
                public void onFailure(@NonNull List<RouterFailure> list, @NonNull RouteOptions routeOptions) {

                }

                @Override
                public void onCanceled(@NonNull RouteOptions routeOptions, @NonNull RouterOrigin routerOrigin) {

                }
            }
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapboxNavigation.unregisterRouteProgressObserver(routeProgressObserver);
    }

    @Override
    protected void onDestroy() {
        mapboxNavigation.onDestroy();
        maneuverApi.cancel();
        super.onDestroy();
    }
}