package com.damnluck.sensordatatransporterwearable.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.damnluck.sensordatatransporterwearable.models.XYZ
import com.google.android.gms.wearable.Node

class Accelerometer(context: Context) : SensorEventListener, NodesReady {
    private val mSensorManager: SensorManager
    private val mSensor: Sensor
    private val context: Context
    private var nodes: List<Node>? = null

    init {
        mSensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        this.context = context
    }

    fun registerSensor(trigger: Boolean) {
        if (trigger) {
            registerSensor()
        } else {
            unregisterSensor()
        }
    }

    private fun registerSensor() {
        mSensorManager.registerListener(this, mSensor, 10000, 20000)
        FindSender(this).execute(context)
    }

    private fun unregisterSensor() {
        mSensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (nodes != null) {
            val xyz = XYZ(event.values[0], event.values[1], event.values[2])
            SendData(context, DataPath.ACCELEROMETER, xyz.toString(), nodes!!).execute()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    override fun getNodes(nodes: List<Node?>?) {
        this.nodes = nodes as List<Node>?
    }

}