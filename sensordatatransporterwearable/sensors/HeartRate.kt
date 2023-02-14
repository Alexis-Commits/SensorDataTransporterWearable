package com.damnluck.sensordatatransporterwearable.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.google.android.gms.wearable.Node

/**
 * This class keeps the logic to send the user's heart rate to the mobile app.
 */
class HeartRate(private val context: Context) : SensorEventListener, NodesReady {
    private val mSensorManager: SensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val mSensor: Sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE)
    private var nodes: List<Node>? = null

    fun registerSensor(trigger: Boolean) {
        if (trigger) {
            registerSensor()
        } else {
            unregisterSensor()
        }
    }

    private fun registerSensor() {
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL)
        FindSender(this).execute(context)
    }

    private fun unregisterSensor() {
        mSensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (nodes != null) {
            val message = event.values[0].toString()
            SendData(context, DataPath.HEART_RATE, message, nodes!!).execute()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}

    override fun getNodes(nodes: List<Node?>?) {
        this.nodes = nodes as List<Node>?
    }

}