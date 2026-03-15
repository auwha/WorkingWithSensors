package com.example.workwithsensors.sensors;

import android.hardware.Sensor;
import com.example.workwithsensors.BaseSensorActivity;
import java.util.Locale;

public class ProximitySensor extends BaseSensorActivity {

    @Override
    public int getSensorType() {
        return Sensor.TYPE_PROXIMITY;
    }

    @Override
    public void updateData(float[] values) {
        float distance = values[0];

        String data = String.format("Dystans: %.2f cm", distance);
        b.textReadings.setText(data);
    }
}