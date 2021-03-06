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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RunActivity extends AppCompatActivity {

    ImageButton startRun, pauseRun, stopRun;
    private TextView distanceRan, avgSpeed;
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

    float initialLatitude;
    float initialLongitude;
    float endLatitude;
    float endLongitude;
    Calendar calendar = Calendar.getInstance();


    DecimalFormat df = new DecimalFormat("#.##");
    SimpleDateFormat mdformat = new SimpleDateFormat("dd / MM / yyyy ");





    DBHelper dbHelper = new DBHelper(this);


    @Override
    protected void onResume() {
        super.onResume();

        if(broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    //textView.setText("" +intent.getExtras().get("overallDistance"));

                    distanceRan.setText("" +intent.getExtras().get("overallDistance"));
                    distance = intent.getIntExtra("overallDistance",1);
                    initialLatitude = intent.getFloatExtra("initialLatitude", 1);
                    initialLongitude = intent.getFloatExtra("initialLongitude", 1);
                    endLongitude = intent.getFloatExtra("endLongitude",1);
                    endLatitude = intent.getFloatExtra("endLatitude", 1);




                }
            };
        }
        registerReceiver(broadcastReceiver,new IntentFilter("location_update"));
    }

    @Override
    protected void onDestroy() {

        //Calendar
        String date =  mdformat.format(calendar.getTime());


        Distance = Integer.toString(distance);

        dbHelper.insertData(Distance, df.format(timeRunning/60000), df.format(averageSpeed), initialLongitude, initialLatitude, endLongitude, endLatitude, date);

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
        startRun =  findViewById(R.id.startRun);
        pauseRun = findViewById(R.id.pauseRun);
        stopRun = findViewById(R.id.stopRun);

        distanceRan = findViewById(R.id.distanceRan);
        avgSpeed = findViewById(R.id.avgSpeed);

        //Chronometer
        chronometer = findViewById(R.id.chronometer);
        stopRun.setVisibility(View.GONE);



        int addedDistance;

        if(!runtime_permissions())
            enable_buttons();


    }

    private void enable_buttons() {

        startRun.setOnClickListener(new View.OnClickListener() {
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
                    stopRun.setVisibility(View.GONE);
                    pauseRun.setVisibility(View.VISIBLE);

                    Running = true;


                }


            }
        });

        pauseRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Running) {

                    chronometer.stop();
                    pauseTime = SystemClock.elapsedRealtime() - chronometer.getBase();

                    pauseRun.setVisibility(View.GONE);
                    stopRun.setVisibility(View.VISIBLE);

                    timeRunning = (SystemClock.elapsedRealtime() - chronometer.getBase());

                   // Toast.makeText(RunActivity.this, "" + timeRunning, Toast.LENGTH_SHORT).show();
                    averageSpeed = getAverageSpeed(distance, timeRunning);
                    avgSpeed.setText(df.format(averageSpeed));


                    Running = false;

                }


            }
        });

        stopRun.setOnClickListener(new View.OnClickListener() {
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

