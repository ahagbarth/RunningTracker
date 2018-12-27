package com.example.runningtracker;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RunActivity extends AppCompatActivity {

    Button button,button2;
    private TextView textView;
    private BroadcastReceiver broadcastReceiver;

    int distance;
    int overallDistance;
    int initialDistance;



    @Override
    protected void onResume() {
        super.onResume();

        if(broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    textView.setText("" +intent.getExtras().get("coordinates"));


                    distance = intent.getIntExtra("overallDistance",1);



                    Toast.makeText(context, ""+ distance, Toast.LENGTH_SHORT).show();




                }
            };
        }
        registerReceiver(broadcastReceiver,new IntentFilter("location_update"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(broadcastReceiver != null){
            unregisterReceiver(broadcastReceiver);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        textView = (TextView) findViewById(R.id.textView);

        int addedDistance;


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(getApplicationContext(),GPSservice.class);
                startService(i);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),GPSservice.class);
                stopService(i);

            }
        });

    }



}

