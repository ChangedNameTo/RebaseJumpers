package com.example.RB.rebasejumpers;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;

/**
 * The type New item activity.
 */
@SuppressWarnings("ALL")
public class NewItemActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {



    /**
     * UI reference.
     */
    private EditText mItemName;

    /**
     * The M reference.
     */
    private final DatabaseReference mReference = FirebaseDatabase
            .getInstance().getReference();

    double latitude;
    double longitude;

    GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_item_creation);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        googleApiClient = new GoogleApiClient.Builder(this, this, this)
                .addApi(LocationServices.API).build();

        if (ContextCompat.checkSelfPermission(NewItemActivity.this, ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { ACCESS_COARSE_LOCATION },
                    1);
        }

        mItemName = (EditText) findViewById(R.id.item_name);

        Button newItemButton = (Button)
                findViewById(R.id.submit_new_item_buttom);
        newItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                newItem();
            }
        });
    }

    /**
     * Puts a new item in the firebase db.
     */
    private void newItem() {
        /*
      The M auth.
     */
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        /*
      The M user.
     */
        FirebaseUser mUser = mAuth.getCurrentUser();
        String name = null;

        if (mUser != null) {
            name = mUser.getEmail().replace(".", "");
        } else {
            Log.w("Warning", "Invalid User");
            return;
        }

        String itemName = mItemName.getText().toString();

        Item newItem = new Item(itemName, name, false, latitude, longitude);

        DatabaseReference newReference =  mReference.child("items").child(name).push();
        newReference.setValue(newItem);

        startActivity(new Intent(NewItemActivity.this, ItemView.class));
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ContextCompat.checkSelfPermission(NewItemActivity.this, ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

            latitude = lastLocation.getLatitude();
            longitude = lastLocation.getLongitude();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // All good!
                } else {
                    Toast.makeText(this, "Need your location!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

   @Override
   protected void onStart() {
       super.onStart();
       if (googleApiClient != null) {
           googleApiClient.connect();
       }
   }

   @Override
   protected void onStop() {
       googleApiClient.disconnect();
       super.onStop();
   }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(NewItemActivity.class.getSimpleName(), "Can't connect to Google Play Services!");
    }
}