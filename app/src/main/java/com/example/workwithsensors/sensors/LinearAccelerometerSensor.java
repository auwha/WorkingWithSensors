package com.example.workwithsensors.sensors;

import android.hardware.Sensor;
import com.example.workwithsensors.BaseSensorActivity;
import java.util.Locale;

public class LinearAccelerometerSensor extends BaseSensorActivity {

    @Override
    public int getSensorType() {
        return Sensor.TYPE_LINEAR_ACCELERATION;
    }

    @Override
    public void updateData(float[] values) {
        String data = String.format(Locale.getDefault(),
                "X: %.2f m/s²\nY: %.2f m/s²\nZ: %.2f m/s²",
                values[0], values[1], values[2]);

        b.textReadings.setText(data);
    }
}