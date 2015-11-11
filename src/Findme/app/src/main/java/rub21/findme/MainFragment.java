package rub21.findme;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.net.URISyntaxException;

import rub21.findme.bean.User;

/**
 * Created by ruben on 10/25/15.
 */
public class MainFragment extends Fragment{
    User user = new User();
    Gson gson = new Gson();
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
        String strtext = getActivity().getIntent().getExtras().getString("user");
        Toast.makeText(getActivity().getApplicationContext(),strtext,Toast.LENGTH_LONG).show();

        mSocket.connect();
        mSocket.emit("new_user", strtext);
        mSocket.on("confirm",confirm);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    /* Remove the locationlistener updates when Activity is paused */
    @Override
    public void onPause() {
        super.onPause();

    }

    private Emitter.Listener confirm = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    User us = gson.fromJson(data.toString(), User.class);
                    //mv.setCenter(new LatLng(us.getLat(), us.getLng()));
                    // mv.setZoom(15);
                    Toast.makeText(getActivity().getApplicationContext(), us.getUser(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    };
}