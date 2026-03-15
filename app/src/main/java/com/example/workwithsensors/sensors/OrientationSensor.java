package com.example.workwithsensors.sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.example.workwithsensors.BaseSensorActivity;

public class OrientationSensor extends BaseSensorActivity {

    private float[] accelerometerReading = new float[3];
    private float[] magnetometerReading = new float[3];

    private final float[] rotationMatrix = new float[9];
    private final float[] orientationAngles = new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Sensor magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if (magnetometer != null) {
            sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    public int getSensorType() {
        return Sensor.TYPE_ACCELEROMETER;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelerometerReading = event.values.clone();
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            magnetometerReading = event.values.clone();
        }

        updateOrientationAngles();
    }

    private void updateOrientationAngles() {
        if (SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerReading, magnetometerReading)) {
            SensorManager.getOrientation(rotationMatrix, orientationAngles);

            updateData(orientationAngles);
        }
    }

    @Override
    public void updateName() {
        b.textName.setText("Poziomica");
    }

    @Override
    public void updateData(float[] values) {
        double pitchDegrees = Math.toDegrees(values[1]);
        double rollDegrees = Math.toDegrees(values[2]);

        String orientationData = String.format("Pitch: %.2f°\n Roll: %.2f°", pitchDegrees, rollDegrees);

        b.textReadings.setText(orientationData);
    }
}