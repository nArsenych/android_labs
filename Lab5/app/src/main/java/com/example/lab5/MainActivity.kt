package com.example.lab5

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelValues = FloatArray(3)
    private var magnetValues = FloatArray(3)

    private lateinit var levelView: LevelView
    private lateinit var angleText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        levelView = findViewById(R.id.levelView)
        angleText = findViewById(R.id.angleText)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
    }

    override fun onResume() {
        super.onResume()
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
        sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        when (event.sensor.type) {
            Sensor.TYPE_ACCELEROMETER -> accelValues = event.values.clone()
            Sensor.TYPE_MAGNETIC_FIELD -> magnetValues = event.values.clone()
        }

        val R = FloatArray(9)
        val I = FloatArray(9)
        if (SensorManager.getRotationMatrix(R, I, accelValues, magnetValues)) {
            val orientation = FloatArray(3)
            SensorManager.getOrientation(R, orientation)
            val rollRad = orientation[2]
            val rollDeg = Math.toDegrees(rollRad.toDouble()).toFloat()

            levelView.setAngle(rollDeg)
            angleText.text = "Кут: ${"%.1f".format(rollDeg)}°"
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
    }
}
