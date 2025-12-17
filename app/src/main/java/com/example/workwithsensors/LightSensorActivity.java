package com.example.workwithsensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import static com.example.workwithsensors.MainActivity.TAG;

import com.example.workwithsensors.databinding.ActivityLightSensorBinding;

public class LightSensorActivity extends AppCompatActivity implements SensorEventListener {
    ActivityLightSensorBinding b;
    private SensorManager sensorManager;
    private Sensor mSensor;
    boolean isSensorPresent;
    private int sensorType = Sensor.TYPE_LIGHT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityLightSensorBinding.inflate(getLayoutInflater());

        EdgeToEdge.enable(this);
        setContentView(b.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(b.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(sensorType) != null ){
            mSensor = sensorManager.getDefaultSensor(sensorType);
            isSensorPresent = true;
            Log.v(TAG,"detect ... sensor");
            b.textName.setText(mSensor.getName());
        } else {
            b.textName.setText("Sensor is not present");
            isSensorPresent = false;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.equals(mSensor)) {
            b.textReadings.setText(sensorEvent.values[0] + "");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG,"---------------> onResume()");

        if (mSensor != null) {
            sensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
            Log.v(TAG,"---------------> OK sensor registerListener");
        }


    }
    @Override
    protected void onPause() {
        super.onPause();
        // unregister a listener. Don't receive any more updates from either sensor
        if (mSensor != null) {
            sensorManager.unregisterListener(this, mSensor);
        }
        Log.v(TAG,"---------------> onPause()");
    }
}