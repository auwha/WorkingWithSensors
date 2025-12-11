package com.example.workwithsensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private static final String TAG = "Mar";
    SensorManager sensorManager;
    private Sensor mLight;
    TextView measurements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        mLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        ListView listView = findViewById(R.id.list_view);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, deviceSensors.stream().map(Sensor::getName).collect(Collectors.toList()));
        listView.setAdapter(arrayAdapter);

        deviceSensors.forEach(sensor -> {
            Log.d(TAG, sensor.getName());
        });

        checkSensorMagnetic();
        listSensorGravity();

        measurements = findViewById(R.id.measurments);
    }

    private void checkSensorMagnetic() {
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if (sensor != null) {
            Log.d(TAG, "checkSensorMagnetic: Success "+sensor);
        } else {
            Log.d(TAG, "checkSensorMagnetic: Failure");
        }
    }

    private void listSensorGravity() {
        Sensor gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (gravitySensor != null) {
            List<Sensor> gravitySensors = sensorManager.getSensorList(Sensor.TYPE_LIGHT);
            gravitySensors.forEach(sensor -> {
                Log.d(TAG, "listSensorGravity: "+sensor.getVendor()+" | "+sensor.getName());
            });
            Log.d(TAG, "listSensorGravity: Success "+gravitySensor);
        } else {
            Log.d(TAG, "listSensorGravity: Failure");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.equals(mLight)) {
            measurements.setText(event.values[0]+"");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}