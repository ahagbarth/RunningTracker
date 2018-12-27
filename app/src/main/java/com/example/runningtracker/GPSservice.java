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
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

public class GPSservice extends Service {

    private LocationListener listener;
    private LocationManager locationManager;


    private double oldLongitude;
    private double oldLatitude;

    String CHANNEL_ID = "1";
    int idNotification = 1;


    public GPSservice() {
    }



    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /*
        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            return super.onStartCommand(intent, flags, startId);
        }
    */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
        if (locationManager != null) {
            //noinspection MissingPermission
            locationManager.removeUpdates(listener);
        }
    }

    @Override
    public void onCreate() {


        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);


        Toast.makeText(this, "Service Created", Toast.LENGTH_SHORT).show();

        listener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {


                double newLongitude = location.getLongitude();
                double newLatitude = location.getLatitude();


                double distance = distanceCalculated(oldLongitude,newLongitude,oldLatitude,newLatitude);
                int Distance = (int) distance;



                Intent i = new Intent("location_update");
                i.putExtra("coordinates", Distance);
                sendBroadcast(i);


                oldLongitude = newLongitude;
                oldLatitude = newLatitude;


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
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 5
                , listener);

    }



    protected double distanceCalculated(double oldLongitude, double newLongitude,double oldLatitude, double newLatitude) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(newLatitude - oldLatitude);
        double lonDistance = Math.toRadians(newLongitude - oldLongitude);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(oldLatitude)) * Math.cos(Math.toRadians(newLatitude))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters



        distance = Math.pow(distance, 2);

        return Math.sqrt(distance);

    }

    private void createNotification() {
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence name = "Running";
            String description = "Running";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name,
                    importance);
            channel.setDescription(description);

            notificationManager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(this, RunActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this,
                CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_running)
                .setContentTitle("Running Tracker")
                .setContentText("Tracking Run")
                .setOngoing(true)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManager.notify(idNotification, mBuilder.build());
        startForeground(idNotification, mBuilder.build());

    }
}
