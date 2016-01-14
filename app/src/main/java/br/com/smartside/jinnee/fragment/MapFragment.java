package br.com.smartside.jinnee.fragment;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import br.com.smartside.jinnee.R;
import br.com.smartside.jinnee.Utils.RecyclerItemClickListener;
import br.com.smartside.jinnee.adapter.RVAdapter;
import br.com.smartside.jinnee.model.Promotions;

/**
 * Created by smartside on 27/10/15.
 */
public class MapFragment extends Fragment implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, OnMapReadyCallback, LocationListener, GoogleApiClient.OnConnectionFailedListener {

    View rootView;
    private GoogleMap map;
    SupportMapFragment mSupportMapFragment;
    private GoogleApiClient mGoogleApiClient;
    protected LocationRequest mLocationRequest;
    double latitude, longitude;
    private List<Promotions> promotions;
    private RecyclerView rv;
    private Boolean isOpen = true;
    private TextView promotion_day, td;
    private LinearLayout layout_promotion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_map, container, false);

        ((SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);

        setHasOptionsMenu(true);

        rv = (RecyclerView) rootView.findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        layout_promotion = (LinearLayout) rootView.findViewById(R.id.promotion_day);

        promotion_day = (TextView) rootView.findViewById(R.id.promotion_day_text);
        promotion_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpandOrRetractPromotions();
            }
        });

        initializeData();

        buildGoogleApiClient();
        mGoogleApiClient.connect();
        createLocationRequest();

        return rootView;
    }

    private void ExpandOrRetractPromotions() {

        if (isOpen) {
            Close();
            isOpen = false;
        } else {
            Open();
            isOpen = true;
        }

    }

    private void Open() {

        //layout_promotion.measure(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        final int targetHeight = 570;

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        //layout_promotion.getLayoutParams().height = 1;
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                layout_promotion.getLayoutParams().height = 570;
                layout_promotion.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(targetHeight / layout_promotion.getContext().getResources().getDisplayMetrics().density));
        layout_promotion.startAnimation(a);

    }

    public void Close() {

        final int initialHeight = layout_promotion.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    //layout_promotion.setVisibility(View.GONE);
                }else{
                    layout_promotion.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime) - 190;
                    layout_promotion.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(initialHeight / layout_promotion.getContext().getResources().getDisplayMetrics().density));
        layout_promotion.startAnimation(a);

    }

    private void initializeData() {

        promotions = new ArrayList<>();

        promotions.add(new Promotions("Óculos Azul", "Descrição do produto ou serviço com detalhe...","R$260,00","R$220,00", R.drawable.produto1));

        RVAdapter adapter = new RVAdapter(getActivity(), promotions);
        rv.setAdapter(adapter);
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
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

        latitude = location.getLatitude();
        longitude = location.getLongitude();

        LatLng me = new LatLng(latitude, longitude);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(me, 17));

        //requestInicialPOI();
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setNumUpdates(1);
        //mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        // mLocationReques.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.mapmenu, menu);
    }

}
