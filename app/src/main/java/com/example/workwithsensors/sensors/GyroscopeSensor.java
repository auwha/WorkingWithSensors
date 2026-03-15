package com.example.workwithsensors.sensors;

import android.hardware.Sensor;
import com.example.workwithsensors.BaseSensorActivity;
import java.util.Locale;

public class GyroscopeSensor extends BaseSensorActivity {

    @Override
    public int getSensorType() {
        return Sensor.TYPE_GYROSCOPE;
    }

    @Override
    public void updateData(float[] values) {
        String data = String.format(
                "X: %.2f rad/s\nY: %.2f rad/s\nZ: %.2f rad/s",
                values[0], values[1], values[2]);

        b.textReadings.setText(data);
    }
}