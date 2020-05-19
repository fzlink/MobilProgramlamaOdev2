package com.example.someandroidfunc;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;

public class LocationActivity extends AppCompatActivity {

    int LOCATION_REQUEST = 1;
    int COARSE_REQUEST = 2;
    int ACTIVITY_RECOGNITION_REQUEST = 3;
    int PERMISSION_REQUESTS = 4;
    private static final String TAG = "LocationActivity";


    private boolean canRequst = false;
    FusedLocationProviderClient fusedLocationProviderClient;

    ImageButton getLocationButton;
    ImageButton sendButton;
    TextView locationText;

    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    double wayLatitude;
    double wayLongitude;



    private TextView stillCounterText;
    private TextView movingCounterText;
    private int stillCount;
    private int movingCount;
    private Button startTrackingActivityButton;

    private void startLocationUpdates(){
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        getLocationButton = findViewById(R.id.getLocationButton);
        sendButton = findViewById(R.id.locationSendButton);
        locationText = findViewById(R.id.latitudeLongtitudeText);
        stillCounterText = findViewById(R.id.stillCounterText);
        movingCounterText = findViewById(R.id.movingCounterText);
        startTrackingActivityButton = findViewById(R.id.startTrackingActivityButton);

        String[] PERMISSIONS = {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACTIVITY_RECOGNITION
        };

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUESTS);
        }
        /*
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            //startLocationUpdates();
        }
        else{
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                Log.d(TAG, "onStart: alert");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQUEST);
            }
            else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQUEST);
            }
        }

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            //startLocationUpdates();
            Log.d(TAG, "onCreate: coarse has perms");
        }
        else{
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)){
                Log.d(TAG, "onStart: alert");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},COARSE_REQUEST);
            }
            else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},COARSE_REQUEST);
            }
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_GRANTED){

            Log.d(TAG, "onCreate: coarse has perms");
        }
        else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACTIVITY_RECOGNITION},ACTIVITY_RECOGNITION_REQUEST);
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACTIVITY_RECOGNITION)){
                Log.d(TAG, "onStart: alert");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACTIVITY_RECOGNITION},ACTIVITY_RECOGNITION_REQUEST);
            }
            else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACTIVITY_RECOGNITION},ACTIVITY_RECOGNITION_REQUEST);
            }
        }
*/

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_NO_POWER);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);


        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    locationText.setText(0 + "\n" + 0);
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        wayLatitude = location.getLatitude();
                        wayLongitude = location.getLongitude();
                        locationText.setText("Latitude: " + wayLatitude + "\nLongtitude: " + wayLongitude);
                    }
                }
            }
        };


        getLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLocationUpdates();
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String whatsAppMessage = "https://maps.google.com/?q=" + wayLatitude + "," + wayLongitude;
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, whatsAppMessage);
                sendIntent.setType("text/plain");
                //sendIntent.setPackage("com.whatsapp");
                startActivity(sendIntent);
            }
        });



        startTrackingActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalBroadcastManager.getInstance(LocationActivity.this).registerReceiver(mActivityBroadcastReceiver,
                        new IntentFilter("activity_intent"));

                startService(new Intent(LocationActivity.this, ActivityDetectionService.class));
            }
        });




    }

    BroadcastReceiver mActivityBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Log.d(TAG, "onReceive()");
            if (intent.getAction().equals("activity_intent")) {
                int type = intent.getIntExtra("type", -1);
                int confidence = intent.getIntExtra("confidence", 0);
                handleUserActivity(type, confidence);
            }
        }
    };

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void handleUserActivity(int type, int confidence) {
        String label = "Unknown";
        switch (type) {
            case DetectedActivity.ON_FOOT:
            case DetectedActivity.WALKING:
            case DetectedActivity.ON_BICYCLE:
            case DetectedActivity.TILTING:
            case DetectedActivity.IN_VEHICLE:
            case DetectedActivity.RUNNING: {
                label = "Moving";
                break;
            }
            case DetectedActivity.STILL: {
                label = "Still";
                break;
            }
        }

        Log.d(TAG, "broadcast:onReceive(): Activity is " + label
                + " and confidence level is: " + confidence);

        if(label == "Moving" ){
            movingCounterText.setText("Moving Counter: " + ++movingCount);
        }else if(label == "Still"){
            stillCounterText.setText("Still Counter: " + ++stillCount);
        }

        Toast.makeText(this,"Activity: " + label +"\nConfidence: " + confidence,Toast.LENGTH_SHORT).show();



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == LOCATION_REQUEST){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //Toast.makeText(this, "Thanks",Toast.LENGTH_SHORT).show();
            }else{
                //Toast.makeText(this, "I need your permission", Toast.LENGTH_SHORT).show();
            }
        }
        else if(requestCode == COARSE_REQUEST){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //Toast.makeText(this, "Thanks",Toast.LENGTH_SHORT).show();
            }else{
                //Toast.makeText(this, "I need your permission", Toast.LENGTH_SHORT).show();
            }
        }
        else if(requestCode == ACTIVITY_RECOGNITION_REQUEST){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //Toast.makeText(this, "Thanks",Toast.LENGTH_SHORT).show();
            }else{
                //Toast.makeText(this, "I need your permission", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
