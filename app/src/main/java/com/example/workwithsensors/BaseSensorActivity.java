package com.example.workwithsensors;

import static com.example.workwithsensors.MainActivity.TAG;

import static java.lang.String.format;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.workwithsensors.databinding.ActivityBaseSensorBinding;

public abstract class BaseSensorActivity extends AppCompatActivity implements SensorEventListener {
    ActivityBaseSensorBinding b;
    private SensorManager sensorManager;
    private Sensor sensor;
    boolean isSensorPresent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityBaseSensorBinding.inflate(getLayoutInflater());

        EdgeToEdge.enable(this);
        setContentView(b.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(b.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(getSensorType());
        isSensorPresent = sensor != null;

        if (isSensorPresent) {
            updateName();
        } else {
            b.textName.setText("No sensor detected");
        }
    }

    public abstract int getSensorType();

    public void updateName() {
        b.textName.setText(sensor.getName());
    }

    public void updateData(float[] values) {
        String value = String.valueOf(values[0]);
        b.textReadings.setText(value);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.equals(sensor)) {
            updateData(sensorEvent.values);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {}

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG,"---------------> onResume()");

        if (!isSensorPresent)
            return;

        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        Log.v(TAG,"---------------> OK sensor registerListener");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG,"---------------> onPause()");

        if (!isSensorPresent)
            return;

        sensorManager.unregisterListener(this, sensor);
    }
}