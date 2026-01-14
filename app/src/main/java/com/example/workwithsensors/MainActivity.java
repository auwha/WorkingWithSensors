package com.example.workwithsensors;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.workwithsensors.databinding.ActivityMainBinding;
import com.example.workwithsensors.sensors.AccelerometerSensor;
import com.example.workwithsensors.sensors.LightSensor;
import com.google.android.material.navigationrail.NavigationRailView;

import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "Mar";
    ActivityMainBinding b;
    SensorManager sensorManager;
    boolean isMenuExpanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityMainBinding.inflate(getLayoutInflater());

        EdgeToEdge.enable(this);
        setContentView(b.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(b.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, deviceSensors.stream().map(Sensor::getName).collect(Collectors.toList()));
        b.listView.setAdapter(arrayAdapter);

        deviceSensors.forEach(sensor -> {
            Log.d(TAG, sensor.getName());
        });

        checkSensorMagnetic();
        listSensorGravity();

        ImageButton btn = b.navigationView.getHeaderView().findViewById(R.id.menu_btn);
        btn.setOnClickListener(v -> {
            isMenuExpanded = !isMenuExpanded;
            b.navigationView.setLabelVisibilityMode(isMenuExpanded ? NavigationRailView.LABEL_VISIBILITY_UNLABELED : NavigationRailView.LABEL_VISIBILITY_LABELED);
        });

        b.navigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.light_sensor_item) {
                Intent intent = new Intent(getApplicationContext(), LightSensor.class);
                startActivity(intent);
            } else if (item.getItemId() == R.id.accelerometer_sensor_item) {
                Intent intent = new Intent(getApplicationContext(), AccelerometerSensor.class);
                startActivity(intent);
            }
            return false;
        });

//        b.lightSensor.setOnClickListener(v -> {
//            Intent intent = new Intent(this, LightSensorActivity.class);
////            intent.putExtra("type", Sensor.TYPE_LIGHT);
//            startActivity(intent);
//        });
//
//        b.accelerometerSensor.setOnClickListener(v -> {
//            Intent intent = new Intent(this, AccelerometerSensor.class);
////            intent.putExtra("type", Sensor.TYPE_LIGHT);
//            startActivity(intent);
//        });
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
}