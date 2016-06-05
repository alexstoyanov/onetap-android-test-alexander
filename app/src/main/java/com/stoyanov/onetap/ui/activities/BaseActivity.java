package com.stoyanov.onetap.ui.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.stoyanov.onetap.R;
import com.stoyanov.onetap.utils.BusProvider;
import com.stoyanov.onetap.utils.Constants;
import com.stoyanov.onetap.utils.PermissionManager;
import com.stoyanov.onetap.utils.SharedPrefsHelper;

import butterknife.ButterKnife;

/**
 * Created by alexander on 6/1/16.
 */
public abstract class BaseActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private static final String LOCATION_TAG = "LocationTag";
    private static final String IS_REQUESTING_LOCATION_UPDATES_KEY = "IsRequestingLocationUpdates";
    private static final String CURRENT_LOCATION_KEY = "CurrentLocation";
    public static final int PERMISSION_USE_LOCATION = 1;

    private GoogleApiClient googleApiClient;
    private boolean isRequestingLocationUpdates;
    private LocationRequest locationRequest;
    private Location currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        initFields();
        updateValuesFromBundle(savedInstanceState);
        createLocationRequest();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    private void initFields() {
        locationRequest = new LocationRequest();
        isRequestingLocationUpdates = false;
        googleApiClient = new GoogleApiClient.Builder(this).
                addApi(LocationServices.API).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).
                build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        BusProvider.getInstance().unregister(this);
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    protected abstract int getLayoutId();

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(LOCATION_TAG, "Connection suspended");
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(LOCATION_TAG, "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }


    protected void createLocationRequest() {
        locationRequest.setInterval(600000);
        locationRequest.setFastestInterval(300000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        startLocationUpdates();
    }

    protected void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            PermissionManager.showPermissionDialog(this, Manifest.permission.ACCESS_FINE_LOCATION,
                    PERMISSION_USE_LOCATION);
        } else {
            if (!isRequestingLocationUpdates) {
                LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest,
                        this);
                isRequestingLocationUpdates = true;
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
        SharedPrefsHelper prefs = SharedPrefsHelper.getInstance(this);
        prefs.putLong(Constants.LATITUDE, Double.doubleToLongBits(location.getLatitude()));
        prefs.putLong(Constants.LONGITUDE, Double.doubleToLongBits(location.getLongitude()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (googleApiClient.isConnected()) {
            startLocationUpdates();
        } else {
            googleApiClient.connect();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (googleApiClient.isConnected()) {
            stopLocationUpdates();
        }
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                googleApiClient, this);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(IS_REQUESTING_LOCATION_UPDATES_KEY,
                isRequestingLocationUpdates);
        savedInstanceState.putParcelable(CURRENT_LOCATION_KEY, currentLocation);
        super.onSaveInstanceState(savedInstanceState);
    }


    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.keySet().contains(IS_REQUESTING_LOCATION_UPDATES_KEY)) {
                isRequestingLocationUpdates = savedInstanceState.getBoolean(
                        IS_REQUESTING_LOCATION_UPDATES_KEY);
            }
            if (savedInstanceState.keySet().contains(CURRENT_LOCATION_KEY)) {
                currentLocation = savedInstanceState.getParcelable(CURRENT_LOCATION_KEY);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_USE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationUpdates();
                } else {
                    Toast.makeText(this,
                            R.string.location_premision_not_granted, Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

}
