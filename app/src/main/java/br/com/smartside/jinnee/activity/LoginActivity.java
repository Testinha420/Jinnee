package br.com.smartside.jinnee.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import br.com.smartside.jinnee.MainActivity;
import br.com.smartside.jinnee.R;

/**
 * Created by smartside on 18/11/15.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, OnMapReadyCallback, LocationListener, GoogleApiClient.OnConnectionFailedListener{

    private CallbackManager callbackManager;
    private GoogleMap map;
    private GoogleApiClient mGoogleApiClient;
    protected LocationRequest mLocationRequest;
    private double longitude, latitude;
    LinearLayout register_layout, login_layout;
    Button bt_login, bt_register, login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getSupportActionBar().hide();

        register_layout = (LinearLayout) findViewById(R.id.layout_register);
        login_layout = (LinearLayout) findViewById(R.id.layout_login);

        bt_login = (Button) findViewById(R.id.bt_login);
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register_layout.setVisibility(View.GONE);
                login_layout.setVisibility(View.VISIBLE);
                bt_login.setBackgroundDrawable(null);
                bt_register.setBackgroundColor(getResources().getColor(R.color.gray));
            }
        });

        bt_register = (Button) findViewById(R.id.bt_register);
        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register_layout.setVisibility(View.VISIBLE);
                login_layout.setVisibility(View.GONE);
                bt_login.setBackgroundColor(getResources().getColor(R.color.gray));
                bt_register.setBackgroundDrawable(null);
            }
        });

        login_button = (Button) findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

        buildGoogleApiClient();
        mGoogleApiClient.connect();
        createLocationRequest();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle) {
        requestOrShowUserLocation();
    }

    private void requestOrShowUserLocation() {
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        // update map
        if(mLastLocation != null){
            showLocationOnMap(mLastLocation);
        }else{
            // toast that we are requesting the device location...
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMyLocationEnabled(true);
    }

    private void showLocationOnMap(Location location) {
        // create a new marker or update if it already exists
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        LatLng me = new LatLng(latitude, longitude);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(me, 17));

        //requestInicialPOI();
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {}

    @Override
    public void onLocationChanged(Location location) {
        showLocationOnMap(location);
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setNumUpdates(1);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onClick(View v) {

    }
}
