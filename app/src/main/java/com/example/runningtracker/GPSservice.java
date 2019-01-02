package com.example.runningtracker;


import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class GPSservice extends Service {
    private LocationListener listener;
    private LocationManager locationManager;


    int overallDistance;

    private double oldLongitude;
    private double oldLatitude;

    String CHANNEL_ID = "1";
    int idNotification = 1;



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {


    //Creates the notification for when service is activated
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Running Tracker";
            String description = "Tracking Run";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager2 = getSystemService(NotificationManager.class);
            notificationManager2.createNotificationChannel(channel);
        }

        Intent intent = new Intent(this, RunActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);




        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_running)
                .setContentTitle("Running Tracker")
                .setContentText("Time ran: " )
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, mBuilder.build());


    //


//Handles The GPS aspect

        Toast.makeText(this, "Service Created", Toast.LENGTH_SHORT).show();
        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);




        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                double newLongitude = location.getLongitude();
                double newLatitude = location.getLatitude();

                double distance = distanceCalculated(oldLongitude,newLongitude,oldLatitude,newLatitude);
                int Distance = (int) distance;

                overallDistance = overallDistance + Distance;

                Intent i = new Intent("location_update");
                //i.putExtra("coordinates", Distance);
                i.putExtra("overallDistance", overallDistance);
                sendBroadcast(i);

                Toast.makeText(GPSservice.this, "" + overallDistance, Toast.LENGTH_SHORT).show();
                oldLongitude = newLongitude;
                oldLatitude = newLatitude;


/*
                Intent i = new Intent("location_update");
                i.putExtra("coordinates", location.getLongitude() + " " + location.getLatitude());
                sendBroadcast(i);
*/

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        };



        //noinspection MissingPermission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 5, listener);
        if(locationManager!=null) {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location!=null) {
                oldLatitude = location.getLatitude();
                oldLongitude = location.getLongitude();
            }
        }
    }


    protected double distanceCalculated(double oldLongitude, double newLongitude,double oldLatitude, double newLatitude) {
/*
        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(newLatitude - oldLatitude);
        double lonDistance = Math.toRadians(newLongitude - oldLongitude);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(oldLatitude)) * Math.cos(Math.toRadians(newLatitude))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters



        distance = Math.pow(distance, 2);

*/
        Location locationA = new Location("LocationA");
        locationA.setLatitude(oldLatitude);
        locationA.setLongitude(oldLongitude);

        Location locationB = new Location("LocationB");
        locationB.setLongitude(newLongitude);
        locationB.setLatitude(newLatitude);

        float distance = locationA.distanceTo(locationB);



        return distance /* Math.sqrt(distance)*/;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(locationManager != null){
            //noinspection MissingPermission
            locationManager.removeUpdates(listener);
        }


    }



}

