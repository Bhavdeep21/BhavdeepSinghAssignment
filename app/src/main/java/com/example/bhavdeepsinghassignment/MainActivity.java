package com.example.bhavdeepsinghassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    boolean isCharging,acCharge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().hide();
        }
    }

    public void checkCharging(View view) {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        ifilter.addAction(Intent.ACTION_POWER_CONNECTED);
        ifilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        Intent batteryStatus = registerReceiver(null, ifilter);
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;
        int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
        if(acCharge && isCharging)
        {
            Intent i=new Intent(getApplicationContext(),charging.class);
            startActivity(i);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Battery is not charging Please connect to ac charger",Toast.LENGTH_LONG).show();
        }
    }

    public void setRemind(View view) {
        Intent i=new Intent(getApplicationContext(),addReminder.class);
        startActivity(i);
    }
}