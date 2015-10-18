package rub21.findeme;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.overlay.Icon;
import com.mapbox.mapboxsdk.overlay.Marker;
import com.mapbox.mapboxsdk.overlay.UserLocationOverlay;
import com.mapbox.mapboxsdk.views.MapView;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import rub21.findeme.bean.User;
import rub21.findeme.server.Config;

import static android.widget.Toast.LENGTH_SHORT;


public class MainFragment extends Fragment implements LocationListener {

    private MapView mv;
    private UserLocationOverlay myLocationOverlay;
    private LocationManager locationManager;
    private String provider;
    User user = new User();
    Gson gson = new Gson();
    //Output
    private TextView txtoutput;

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(Config.server);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frame_main, container, false);

        //MAP
        mv = (MapView) view.findViewById(R.id.mapview);
        mv.setMinZoomLevel(mv.getTileProvider().getMinimumZoomLevel());
        mv.setMaxZoomLevel(mv.getTileProvider().getMaximumZoomLevel());
        mv.setCenter(mv.getTileProvider().getCenterCoordinate());
        mv.setZoom(0);
        mv.setUserLocationEnabled(true);

        //USER
        String str_user = getActivity().getIntent().getExtras().getString("user");
        user = gson.fromJson(str_user,User.class);

        // LOCATION
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            user.setLoc_status(true);
            onLocationChanged(location);
        }
        //IO
        mSocket.emit("new_user", str_user);
        mSocket.on("confirm", onResult);
        mSocket.on("friends", friends);
        mSocket.connect();
        //txtoutput.setText("dd");

        return view;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(provider, 400, 1, (LocationListener) this);
    }

    /* Remove the locationlistener updates when Activity is paused */
    @Override
    public void onPause() {
        super.onPause();
        locationManager.removeUpdates((LocationListener) this);
    }

    @Override
    public void onLocationChanged(Location location) {
        user.setLat(location.getLatitude());
        user.setLng(location.getLongitude());
        Toast.makeText(getActivity().getApplicationContext(), user.getLat().toString(), Toast.LENGTH_SHORT).show();
        if (user.isLoc_status()) {
            String json = gson.toJson(user);
            mSocket.emit("location", json);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(getActivity(), "Enabled new provider " + provider,  Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(getActivity(), "Disabled provider " + provider, LENGTH_SHORT).show();
    }

    //IO
    private Emitter.Listener onResult = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String user;
                    try {
                        user = data.getString("b1887c22a5d7bd47");
                        Marker m = new Marker(mv, "Edinburgh", "Scotland", new LatLng(55.94629, -3.20777));
                        m.setIcon(new Icon(getActivity().getApplicationContext(), Icon.Size.SMALL, "marker-stroked", "ee8a65"));
                        mv.addMarker(m);
                    } catch (JSONException e) {
                        return;
                    }

                }
            });
        }
    };

    private Emitter.Listener friends = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                        User  us =gson.fromJson(data.toString(), User.class);

                        Marker m = new Marker(mv, us.getUser(), us.getIdphone(), new LatLng(us.getLat(), us.getLng()));
                        m.setIcon(new Icon(getActivity().getApplicationContext(), Icon.Size.SMALL, "marker-stroked", "ee8a65"));
                        mv.addMarker(m);
                }
            });
        }
    };

}
