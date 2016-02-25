package help.com.help.main;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class NeedyActivity extends Activity {

    private void findOutLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                System.out.println((location));
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
        System.out.println(l);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findOutLocation();
    }

}
