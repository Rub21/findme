package rub21.main;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.LinkedList;
import java.util.List;
import rub21.main.io.Server;

public class Main extends Activity implements LocationListener {

   // private TextView latituteField;
    //private TextView longitudeField;
    private LocationManager locationManager;
    private String provider;
    private Context context;
    //user
    private String username = "User";
    // User name
    private EditText et_Username;
    // Sign In
    private Button bt_SignIn;
    // Message
    private TextView tv_Message;
    Server server = new Server();
    String android_id = null;

    //flag
    private boolean isenable = false;
    List<Double> coordinates = new LinkedList<Double>();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

       // latituteField = (TextView) findViewById(R.id.TextView02);
        //longitudeField = (TextView) findViewById(R.id.TextView04);
        
        
        
        
        
        
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        } else {
           // latituteField.setText("Location not available");
           // longitudeField.setText("Location not available");
        }

        //id
        android_id = Secure.getString(getContentResolver(), Secure.ANDROID_ID);
        //user
        // Initialization
        et_Username = (EditText) findViewById(R.id.et_Username);
        bt_SignIn = (Button) findViewById(R.id.bt_SignIn);
        tv_Message = (TextView) findViewById(R.id.tv_Message);
        bt_SignIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // Stores User name
                username = String.valueOf(et_Username.getText());
                tv_Message.setText("Login successful  " + username + "!!!");
                if (!username.equals("User")) {
                    isenable = true;
                    server.postData(coordinates, username, android_id);
                }
            }
        });

    }

    /* Request updates at startup */
    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(provider, 400, 1, (LocationListener) this);
    }

    /* Remove the locationlistener updates when Activity is paused */
    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates((LocationListener) this);
    }

    @Override
    public void onLocationChanged(Location location) {
        coordinates.clear();
        double lat = (double) (location.getLatitude());
        double lng = (double) (location.getLongitude());
        coordinates.add(lat);
        coordinates.add(lng);
        //latituteField.setText(String.valueOf(lat));
        //longitudeField.setText(String.valueOf(lng));
        if (isenable) {
            server.postData(coordinates, username, android_id);
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
        
    }
}
