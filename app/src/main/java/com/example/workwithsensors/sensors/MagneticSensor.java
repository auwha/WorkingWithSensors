package com.example.workwithsensors.sensors;

import android.hardware.Sensor;
import com.example.workwithsensors.BaseSensorActivity;
import java.util.Locale;

public class MagneticSensor extends BaseSensorActivity {

    @Override
    public int getSensorType() {
        return Sensor.TYPE_MAGNETIC_FIELD;
    }

    @Override
    public void updateData(float[] values) {
        String data = String.format(
                "X: %.2f µT\nY: %.2f µT\nZ: %.2f µT",
                values[0], values[1], values[2]);

        b.textReadings.setText(data);
    }
}