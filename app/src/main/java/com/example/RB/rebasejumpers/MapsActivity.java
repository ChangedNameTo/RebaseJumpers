package com.example.RB.rebasejumpers;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.*;

/**
 * The type Maps activity.
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final float MIN_ZOOM = 12.0f;
    private static final float MAX_ZOOM = 14.0f;
    private static final double ATL_LAT = 33.779721;
    private static final double ATL_LON = -84.399186;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        //noinspection ChainedMethodCall Needs chaining
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     * @param mMap the Google Map
     */
    @Override
    public void onMapReady(final GoogleMap mMap) {

        // Sets default zooms
        mMap.setMinZoomPreference(MIN_ZOOM);
        mMap.setMaxZoomPreference(MAX_ZOOM);

        LatLng atlanta = new LatLng(ATL_LAT, ATL_LON);
//        mMap.addMarker(new MarkerOptions().position(atlanta).title("Marker in Atlanta"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(atlanta));

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("items");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                            for(DataSnapshot a: postSnapshot.getChildren()) {
                                String itemName = a.child("itemName").getValue().toString();
                                Object latitude = a.child("latitude").getValue();
                                Object longitude = a.child("longitude").getValue();
                                LatLng itemLoc = new LatLng((double) latitude, (double) longitude);
                                mMap.addMarker(new MarkerOptions().position(itemLoc).title(itemName));
                                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                    @Override
                                    public boolean onMarkerClick(Marker marker) {
                                        marker.showInfoWindow();
                                        return true;
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }

        );
    }
}
