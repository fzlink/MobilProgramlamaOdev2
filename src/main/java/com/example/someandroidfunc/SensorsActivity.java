package com.example.someandroidfunc;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.widget.EditText;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;

public class SensorsActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private EditText sensorListEditText;
    private TextView luxText;
    private TextView luxHintText;
    private TextView accelText;
    private TextView luxValText;
    private TextView accelValText;
    private TextView sensorTimerText;
    private ConstraintLayout layout;

    private Sensor light;
    private Sensor accelerometer;
    private double currentAccel;
    private double prevAccel;
    private DecimalFormat decimalFormat1;
    private DecimalFormat decimalFormat5;


    long startTime = 0;
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            long milliseconds = System.currentTimeMillis() - startTime;
            double seconds = milliseconds / 1000.0;
            sensorTimerText.setText("Timer: " + decimalFormat1.format(seconds));

            if(seconds >=5){
                Toast.makeText(getApplicationContext(),"Exceeded 5 seconds. App will close.", Toast.LENGTH_SHORT).show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(),LogInActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("Exit",true);
                        startActivity(intent);
                        finish();
                    }
                }, 1000);
            }else{
                timerHandler.postDelayed(this, 500);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);

        sensorListEditText = findViewById(R.id.sensorListEditText);
        luxText = findViewById(R.id.luxText);
        luxHintText = findViewById(R.id.luxHintText);
        accelText = findViewById(R.id.accelText);
        luxValText = findViewById(R.id.luxValText);
        accelValText = findViewById(R.id.accelValText);
        sensorTimerText = findViewById(R.id.sensorTimerText);
        layout = findViewById(R.id.sensorsLayout);

        luxValText.setText("0");
        accelValText.setText("0");

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);

        for(int i=0;i<sensorList.size();i++){
            sensorListEditText.append(i + " - " + sensorList.get(i).getName() + "\n");
        }
        sensorListEditText.setScroller(new Scroller(this));
        sensorListEditText.setVerticalScrollBarEnabled(true);
        sensorListEditText.setMovementMethod(new ScrollingMovementMethod());
        sensorListEditText.setKeyListener(null);

        decimalFormat1 = new DecimalFormat("#.#");
        decimalFormat5 = new DecimalFormat("#.#####");
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor == light){
            float lux = event.values[0];
            luxValText.setText(decimalFormat5.format(lux));

            if(lux < 10){
                layout.setBackgroundColor(Color.BLACK);
                sensorListEditText.setTextColor(Color.WHITE);
                luxValText.setTextColor(Color.WHITE);
                accelValText.setTextColor(Color.WHITE);
                luxText.setTextColor(Color.WHITE);
                luxHintText.setTextColor(Color.WHITE);
                accelText.setTextColor(Color.WHITE);
                sensorTimerText.setTextColor(Color.WHITE);
            }
            else{
                layout.setBackgroundColor(Color.WHITE);
                sensorListEditText.setTextColor(Color.BLACK);
                luxValText.setTextColor(Color.BLACK);
                accelValText.setTextColor(Color.BLACK);
                luxText.setTextColor(Color.BLACK);
                luxHintText.setTextColor(Color.BLACK);
                accelText.setTextColor(Color.BLACK);
                sensorTimerText.setTextColor(Color.BLACK);

            }

        }
        else if(event.sensor == accelerometer){
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            prevAccel = currentAccel;

            currentAccel = Math.sqrt(x*x + y*y + z*z);
            accelValText.setText(decimalFormat5.format(currentAccel));
            double delta = Math.abs(currentAccel - prevAccel);
            if( delta < 0.05){
                accelValText.setText(decimalFormat5.format(currentAccel )+ "(Stable)");
            }
            else{
                timerHandler.removeCallbacks(timerRunnable);
                startTime = System.currentTimeMillis();
                timerHandler.postDelayed(timerRunnable, 0);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,light,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
