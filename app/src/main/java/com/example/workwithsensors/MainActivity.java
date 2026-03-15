package com.example.workwithsensors;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.workwithsensors.databinding.ActivityMainBinding;
import com.example.workwithsensors.sensors.AccelerometerSensor;
import com.example.workwithsensors.sensors.GravitySensor;
import com.example.workwithsensors.sensors.GyroscopeSensor;
import com.example.workwithsensors.sensors.LightSensor;
import com.example.workwithsensors.sensors.LinearAccelerometerSensor;
import com.example.workwithsensors.sensors.MagneticSensor;
import com.example.workwithsensors.sensors.OrientationSensor;
import com.example.workwithsensors.sensors.ProximitySensor;
import com.example.workwithsensors.sensors.SignificantMotionSensor;
import com.example.workwithsensors.sensors.StepDetectorSensor;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigationrail.NavigationRailView;

import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "Mar";
    ActivityMainBinding b;
    SensorManager sensorManager;

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

        setSupportActionBar(b.toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, b.drawer, b.toolbar, R.string.nav_open, R.string.nav_close);
        b.drawer.addDrawerListener(toggle);

        toggle.syncState();

        b.navView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.light_sensor_item) {
                Intent intent = new Intent(getApplicationContext(), LightSensor.class);
                startActivity(intent);
            } else if (item.getItemId() == R.id.accelerometer_sensor_item) {
                Intent intent = new Intent(getApplicationContext(), AccelerometerSensor.class);
                startActivity(intent);
            } else if (item.getItemId() == R.id.orientation_sensor_item) {
                Intent intent = new Intent(getApplicationContext(), OrientationSensor.class);
                startActivity(intent);
            } else if (item.getItemId() == R.id.gravity_sensor_item) {
                Intent intent = new Intent(getApplicationContext(), GravitySensor.class);
                startActivity(intent);
            } else if (item.getItemId() == R.id.gyroscope_sensor_item) {
                startActivity(new Intent(this, GyroscopeSensor.class));
            } else if (item.getItemId() == R.id.magnetic_sensor_item) {
                startActivity(new Intent(this, MagneticSensor.class));
            } else if (item.getItemId() == R.id.linear_accelerometer_item) {
                startActivity(new Intent(this, LinearAccelerometerSensor.class));
            } else if (item.getItemId() == R.id.proximity_sensor_item) {
                startActivity(new Intent(this, ProximitySensor.class));
            } else if (item.getItemId() == R.id.step_detector_item) {
                startActivity(new Intent(this, StepDetectorSensor.class));
            } else if (item.getItemId() == R.id.significant_motion_item) {
                startActivity(new Intent(this, SignificantMotionSensor.class));
            }

            b.drawer.closeDrawers();
            return true;
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (b.drawer.isDrawerOpen(GravityCompat.START)) {
                    b.drawer.closeDrawer(GravityCompat.START);
                } else {
                    finish();
                }
            }
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