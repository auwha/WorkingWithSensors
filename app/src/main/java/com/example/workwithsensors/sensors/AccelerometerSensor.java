package com.example.workwithsensors.sensors;

import android.hardware.Sensor;

import com.example.workwithsensors.BaseSensorActivity;

public class AccelerometerSensor extends BaseSensorActivity {

    @Override
    public int getSensorType() {
        return Sensor.TYPE_ACCELEROMETER;
    }
}