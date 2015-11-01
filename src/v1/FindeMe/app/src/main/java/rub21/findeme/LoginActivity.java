package rub21.findeme;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import rub21.findeme.bean.User;

import static android.widget.Toast.LENGTH_LONG;

/**
 * Created by ruben on 10/11/15.
 */
public class LoginActivity extends Activity {
    private String provider;
    private Context context;
    //Componentes
    private EditText txfUsuario ;
    private Spinner spnChanel;
    private CheckBox chkMuestrame;
    private Button btFindme;
    User user = new User();
    Gson gson = new Gson();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //botones
        txfUsuario = (EditText) findViewById(R.id.txfUsuario);
        spnChanel=(Spinner) findViewById(R.id.spnChanel);
        btFindme = (Button) findViewById(R.id.btFindme);
        chkMuestrame = (CheckBox) findViewById(R.id.chkMuestrame);

        //Enable GPS
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
        }else{
            showGPSDisabledAlertToUser();
        }

        //Evento find me
        btFindme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setUser(txfUsuario.getText().toString());
                user.setChanel(String.valueOf(spnChanel.getSelectedItem()));
                user.setIdphone(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
                user.setStatus(chkMuestrame.isChecked());
                if (!user.getUser().isEmpty()) {
                    user.setStatus(true);
                    String json = gson.toJson(user);
                    //Toast.makeText(getApplicationContext(),json, LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.putExtra("user",json);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Add a user", LENGTH_LONG).show();
                }
            }
        });
    }

    private void showGPSDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
}
