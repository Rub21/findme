package rub21.findeme.server;

import android.content.Intent;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.emitter.Emitter;



import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import rub21.findeme.bean.User;

/**
 * Created by ruben on 10/7/15.
 */
public class SocketIO {

    Gson gson = new Gson();

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(Config.server);
        } catch (URISyntaxException e) {}
    }

    public SocketIO(){
        mSocket.connect();

    }

    public  void sendlocation(User user){
        String json = gson.toJson(user);
        mSocket.emit("location", json);
    }





    private Emitter.Listener onResult = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

        }
    };

//
//    mSocket.on("message", onResult);


}
