package help.com.help.main;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

import help.com.help.R;

public class VolunteerActivity extends FragmentActivity implements OnMapReadyCallback {

    private Map<String, LatLng> requests = new HashMap<>();

    private Location findOutLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        String provider = LocationManager.GPS_PROVIDER;
        locationManager.requestLocationUpdates(provider, 0, 0, locationListener);
        return locationManager.getLastKnownLocation(provider);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Firebase ref = new Firebase("https://blistering-inferno-7485.firebaseio.com/");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.child("users").getChildren()) {
                    LatLng coord = new LatLng(
                            Double.parseDouble(data.child("latitude").getValue().toString()),
                            Double.parseDouble(data.child("longitude").getValue().toString()));
                    System.out.println(coord.latitude + " " + coord.longitude);

                    requests.put((String) data.child("uid").getValue(), coord);
//                    onMapReady();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Location l = findOutLocation();

        //your coordinates
        LatLng coordinates = new LatLng(l.getLatitude(), l.getLongitude());

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                coordinates, 16));

        for (String id : requests.keySet()) {
            // You can customize the marker image using images bundled with
            // your app, or dynamically generated bitmaps.
            googleMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher))
                    .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                    .position(requests.get(id)));
        }
    }
}
