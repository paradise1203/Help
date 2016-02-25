package help.com.help.main;

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
import help.com.help.auth.UserUid;

public class VolunteerActivity extends FragmentActivity implements OnMapReadyCallback {

    private Map<UserUid, LatLng> requests = new HashMap<>();

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
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    requests.putAll((Map<? extends UserUid, ? extends LatLng>) data.getValue());
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

        //your coordinates
        LatLng coordinates = ;

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                coordinates, 16));

        for (UserUid id : requests.keySet()) {
            // You can customize the marker image using images bundled with
            // your app, or dynamically generated bitmaps.
            googleMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher))
                    .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                    .position(requests.get(id)));
        }
    }
}
