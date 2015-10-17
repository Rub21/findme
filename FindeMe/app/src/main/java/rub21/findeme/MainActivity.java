package rub21.findeme;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MainActivity  extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle recupera = getIntent().getExtras();
//        if(recupera != null){
//            String user = recupera.getString("user");
//            Bundle bundle = new Bundle();
//            bundle.putString("user", user);
//            MainFragment  mainFragment = new MainFragment();
//            mainFragment.setArguments(bundle);
//        }
    }
}
