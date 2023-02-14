package com.damnluck.sensordatatransporterwearable.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.google.android.gms.wearable.Node

/**
 * This class keeps the logic to send the watch's steps to the mobile app.
 */
class Steps(private val context: Context) : SensorEventListener, NodesReady {
    private val mSensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val mSensor: Sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
    private var nodes: List<Node>? = null

    fun startSensor() {
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL)
        FindSender(this).execute(context)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (nodes != null) {
            val message = event.values[0].toString()
            SendData(context, DataPath.STEP_COUNT, message, nodes!!).execute()
            mSensorManager.unregisterListener(this, mSensor)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}

    override fun getNodes(nodes: List<Node?>?) {
        this.nodes = nodes as List<Node>?
    }
}