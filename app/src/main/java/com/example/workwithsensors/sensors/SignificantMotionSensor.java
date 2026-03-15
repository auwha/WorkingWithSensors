package com.example.workwithsensors.sensors;

import android.hardware.Sensor;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.os.Bundle;
import com.example.workwithsensors.BaseSensorActivity;

public class SignificantMotionSensor extends BaseSensorActivity {

    private TriggerEventListener triggerEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        triggerEventListener = new TriggerEventListener() {
            @Override
            public void onTrigger(TriggerEvent event) {
                updateData(event.values);

                requestTrigger();
            }
        };

        requestTrigger();
    }

    private void requestTrigger() {
        if (sensor != null) {
            sensorManager.requestTriggerSensor(triggerEventListener, sensor);
        }
    }

    @Override
    public int getSensorType() {
        return Sensor.TYPE_SIGNIFICANT_MOTION;
    }

    @Override
    public void updateName() {
        b.textName.setText("Wykrywanie Znaczącego Ruchu");
    }

    @Override
    public void updateData(float[] values) {
        b.textReadings.setText("WYKRYTO RUCH!\nUrządzenie zmieniło swoje położenie.");
        b.textReadings.setTextColor(android.graphics.Color.RED);
    }
}