package com.example.runningtracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class RunActivity extends AppCompatActivity {

    ImageButton button,button2;
    private TextView textView;
    private BroadcastReceiver broadcastReceiver;

    Chronometer chronometer;
/*
    public enum State {
        Running,
        Paused,
        Stopped
    }

    protected State state;

*/
    int distance;
    String Distance;
    int overallDistance;
    String Chronometer;


    int initialDistance;

    DBHelper dbHelper = new DBHelper(this);


    @Override
    protected void onResume() {
        super.onResume();

        if(broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    textView.setText("" +intent.getExtras().get("overallDistance"));


                    distance = intent.getIntExtra("overallDistance",1);






//Average speed formula:
                    // speed = distance / Time


                }
            };
        }
        registerReceiver(broadcastReceiver,new IntentFilter("location_update"));
    }

    @Override
    protected void onDestroy() {
        //int avg_speed = distance / time;
        //String avgSpeed = Integer.toString(avg_speed);

        Distance = Integer.toString(distance);
        dbHelper.insertData(Distance, "asdfasdf");




        super.onDestroy();
        if(broadcastReceiver != null){
            unregisterReceiver(broadcastReceiver);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        //this.state = State.Stopped;

        chronometer = findViewById(R.id.chronometer);


        button =  findViewById(R.id.imageButton);
        button2 = findViewById(R.id.imageButton2);
        textView = (TextView) findViewById(R.id.textView);

        int addedDistance;


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i =new Intent(getApplicationContext(),GPSservice.class);
                startService(i);
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();

                //button.setBackgroundResource(R.drawable.ic_stop_black_24dp);

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),GPSservice.class);
                stopService(i);
                chronometer.stop();
                Chronometer = chronometer.getText().toString();
               // Toast.makeText(RunActivity.this, chronometer.getText().toString(), Toast.LENGTH_SHORT).show();

            }
        });



    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putString("DistanceSaved" ,Distance);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Distance = savedInstanceState.getString("DistanceSaved");

    }
}

