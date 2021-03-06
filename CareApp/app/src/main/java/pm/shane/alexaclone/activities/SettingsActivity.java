package pm.shane.alexaclone.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import pm.shane.alexaclone.MainApp;
import pm.shane.alexaclone.R;
import pm.shane.alexaclone.SessionManager;
import pm.shane.alexaclone.location.LocationUpdatesListner;
import pm.shane.alexaclone.services.AlexaService;
import pm.shane.alexaclone.services.CameraService;

/**
 * Created by Shane on 28/10/2017.
 */

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        showBackButton();
    }

    public void onSignOutClicked(View view) {
        SessionManager.setLoggedIn(false);
        MainApp.get().stopService(new Intent(MainApp.get(), AlexaService.class));
        MainApp.get().stopService(new Intent(MainApp.get(), CameraService.class));
        MainApp.get().stopService(new Intent(MainApp.get(), LocationUpdatesListner.class));
        Intent myIntent = new Intent(this, ModeActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(myIntent);
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