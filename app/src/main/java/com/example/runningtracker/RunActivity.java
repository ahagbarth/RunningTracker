package com.example.runningtracker;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class RunActivity extends AppCompatActivity {

    ImageButton button,button2, button3;
    private TextView textView, avgSpeed;
    private BroadcastReceiver broadcastReceiver;

    //Chronometer
    Chronometer chronometer;
    String Chronometer;
    boolean Running;
    long pauseTime;
    float timeRunning;

    int distance;
    String Distance;

    double averageSpeed;
    String avg_Speed;

    DecimalFormat df = new DecimalFormat("#.##");




    int initialDistance;

    DBHelper dbHelper = new DBHelper(this);


    @Override
    protected void onResume() {
        super.onResume();

        if(broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    //textView.setText("" +intent.getExtras().get("overallDistance"));

                    textView.setText("" +intent.getExtras().get("overallDistance"));
                    distance = intent.getIntExtra("overallDistance",1);


                }
            };
        }
        registerReceiver(broadcastReceiver,new IntentFilter("location_update"));
    }

    @Override
    protected void onDestroy() {


        Distance = Integer.toString(distance);

        dbHelper.insertData(Distance, df.format(timeRunning/60000));

        super.onDestroy();
        if(broadcastReceiver != null){
            unregisterReceiver(broadcastReceiver);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        //Linking buttons with view
        button =  findViewById(R.id.imageButton);
        button2 = findViewById(R.id.imageButton2);
        button3 = findViewById(R.id.imageButton3);

        textView = findViewById(R.id.textView);
        avgSpeed = findViewById(R.id.avgSpeed);

        //Chronometer
        chronometer = findViewById(R.id.chronometer);
        button3.setVisibility(View.GONE);


        int addedDistance;

        if(!runtime_permissions())
            enable_buttons();


    }

    private void enable_buttons() {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Running) {
                    //Start Service
                    Intent i =new Intent(getApplicationContext(),GPSservice.class);
                    startService(i);

                    //Start Chronometer
                    chronometer.setBase(SystemClock.elapsedRealtime() - pauseTime);
                    chronometer.start();

                    //Change Button
                    button3.setVisibility(View.GONE);
                    button2.setVisibility(View.VISIBLE);

                    Running = true;


                }


            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Running) {

                    chronometer.stop();
                    pauseTime = SystemClock.elapsedRealtime() - chronometer.getBase();

                    button2.setVisibility(View.GONE);
                    button3.setVisibility(View.VISIBLE);

                    timeRunning = (SystemClock.elapsedRealtime() - chronometer.getBase());

                   // Toast.makeText(RunActivity.this, "" + timeRunning, Toast.LENGTH_SHORT).show();
                    averageSpeed = getAverageSpeed(distance, timeRunning);
                    avgSpeed.setText(df.format(averageSpeed));


                    Running = false;

                }


            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Stopping Service
                Intent i = new Intent(getApplicationContext(),GPSservice.class);
                stopService(i);

                chronometer.stop();
                chronometer.setBase(SystemClock.elapsedRealtime());
                pauseTime = 0;
            }
        });

    }
    private double getAverageSpeed(double distance, float time) {

        double s = distance;
        float t = time;

        double averageSpd = s / (t / 1000);


        return averageSpd;
       //avgSpeed.setText(df.format(avg_Speed));



    }

    private boolean runtime_permissions() {
        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},100);

            return true;
        }
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100){
            if( grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                enable_buttons();
            }else {
                runtime_permissions();
            }
        }
    }


}

