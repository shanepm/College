package pm.shane.alexaclone.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import pm.shane.alexaclone.MainApp;
import pm.shane.alexaclone.R;
import pm.shane.alexaclone.SessionManager;

/**
 * Created by Shane on 28/10/2017.
 */

public class HomeActivity extends AppCompatActivity {

    private Switch lightsSwitch;
    private Switch heatingSwitch;
    private LinearLayout remoteCameraView;
    private String type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        showBackButton();
        lightsSwitch = findViewById(R.id.lights_switch);
        heatingSwitch = findViewById(R.id.heating_switch);
        remoteCameraView = findViewById(R.id.remote_camera_view);
        if (!SessionManager.isLoggedIn()) {
            remoteCameraView.setVisibility(View.GONE);
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            type = bundle.getString("type");
            if (type != null) {
                switch (type){
                    case "heating-on": heatingSwitch.setChecked(true); break;
                    case "heating-off": heatingSwitch.setChecked(false); break;
                    case "lights-on": lightsSwitch.setChecked(true); break;
                    case "lights-off": lightsSwitch.setChecked(false); break;
                }
            }
        }
    }

    public void onCurrentTimeClicked(View view) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.US);
        if (MainApp.canSpeak()) {
            MainApp.speak(getString(R.string.the_time_is, sdf.format(cal.getTime())));
        }
        Toast.makeText(MainApp.getContext(), getString(R.string.the_time_is, sdf.format(cal.getTime())), Toast.LENGTH_LONG).show();
    }

    public void onWeatherClicked(View view) {
        Intent myIntent = new Intent(this, WeatherActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(myIntent);
    }

    public void onPlayMusicClicked(View view) {
        Intent myIntent = new Intent(this, MusicActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(myIntent);
    }

    public void onToggleLightsClicked(View view) {
        boolean state = !lightsSwitch.isChecked();
        lightsSwitch.setChecked(state);
        if (MainApp.getConnectedDevice() == null) {
            Toast.makeText(MainApp.getContext(), R.string.connect_device_first, Toast.LENGTH_SHORT).show();
            lightsSwitch.setChecked(false);
            return;
        }
        MainApp.getConnectedDevice().digitalWrite(6, state);
    }

    public void onToggleHeatingClicked(View view) {
        boolean state = !heatingSwitch.isChecked();
        heatingSwitch.setChecked(state);
        if (MainApp.getConnectedDevice() == null) {
            Toast.makeText(MainApp.getContext(), R.string.connect_device_first, Toast.LENGTH_SHORT).show();
            heatingSwitch.setChecked(false);
            return;
        }
        MainApp.getConnectedDevice().digitalWrite(7, state);
    }

    public void onToggleCameraClicked(View view) {}

    public void onOrderTakeawayClicked(View view) {
        Intent intent = new Intent(MainApp.getContext(), PlaceActivity.class);
        intent.putExtra("type", "take-away");
        startActivity(intent);
    }

    public void onOrderShoppingClicked(View view) {
        Intent myIntent = new Intent(this, ShoppingActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(myIntent);
    }

    public void onOrderTaxiClicked(View view) {
        Intent intent = new Intent(MainApp.getContext(), PlaceActivity.class);
        intent.putExtra("type", "taxi");
        startActivity(intent);
    }

    private void showBackButton() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}