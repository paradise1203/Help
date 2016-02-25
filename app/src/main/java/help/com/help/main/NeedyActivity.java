package help.com.help.main;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.client.Firebase;

import help.com.help.R;
import help.com.help.auth.UserUid;

public class NeedyActivity extends Activity {

    private void findOutLocation() {
        final Firebase ref = new Firebase("https://blistering-inferno-7485.firebaseio.com/");

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
        Location l = locationManager.getLastKnownLocation(provider);

        Firebase userRef = ref.child("users").child(UserUid.getUid());
        userRef.child("latitude").setValue(l.getLatitude());
        userRef.child("longitude").setValue(l.getLongitude());
        userRef.child("volunteer").setValue(null);
        System.out.println(l);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.needy_page);
        final Button help = (Button) findViewById(R.id.help);

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findOutLocation();
            }
        });
    }

}
