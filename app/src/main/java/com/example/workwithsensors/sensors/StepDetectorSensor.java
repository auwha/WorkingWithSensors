package com.example.workwithsensors.sensors;

import android.hardware.Sensor;
import com.example.workwithsensors.BaseSensorActivity;

public class StepDetectorSensor extends BaseSensorActivity {

    private int stepsInSession = 0;

    @Override
    public int getSensorType() {
        return Sensor.TYPE_STEP_DETECTOR;
    }

    @Override
    public void updateData(float[] values) {
        if (values[0] == 1.0) {
            stepsInSession++;
        }

        String data = "Wykryto krok: " + stepsInSession + " kroków";

        b.textReadings.setText(data);
    }
}