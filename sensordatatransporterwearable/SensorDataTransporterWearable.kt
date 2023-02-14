package com.damnluck.sensordatatransporterwearable

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.damnluck.sensordatatransporterwearable.core.MessageReceiverWear
import com.damnluck.sensordatatransporterwearable.sensors.Accelerometer
import com.damnluck.sensordatatransporterwearable.sensors.Gyroscope
import com.damnluck.sensordatatransporterwearable.sensors.HeartRate
import com.damnluck.sensordatatransporterwearable.sensors.Steps

/**
 * This class provides an interface to use the library.
 * It just needs the context or the activity of the app to operate.
 */
class SensorDataTransporterWearable(private val context: Context) {
    private var heartrate: HeartRate? = null
    private var accelerometer: Accelerometer? = null
    private var gyroscope: Gyroscope? = null
    private var steps: Steps? = null
    private var messageReceiver: MessageReceiver? = null

    fun initHeartRateSensor() {
        heartrate = HeartRate(context)
    }

    fun initAccelerometerSensor() {
        accelerometer =
            Accelerometer(context)
    }

    fun initGyroscopeSensor() {
        gyroscope = Gyroscope(context)
    }

    fun initStepCounterSensor() {
        steps = Steps(context)
    }

    fun registerBroadcastReceiver() {
        val messageFilter = IntentFilter(Intent.ACTION_SEND)
        messageReceiver = MessageReceiver()
        LocalBroadcastManager.getInstance(context)
            .registerReceiver(messageReceiver!!, messageFilter)
    }

    fun unRegisterBroadcastReceiver() {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(messageReceiver!!)
    }

    inner class MessageReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val message = intent.getStringExtra("action")
            var action = 0
            if (message != null) {
                action = message.toInt()
            }
            checkHeartRate(action)
            checkAccelerometer(action)
            checkGyroscope(action)
            checkStepCount(action)
        }
    }

    private fun checkHeartRate(action: Int) {
        if (action == MessageReceiverWear.HEART_RATE_START_SENSOR) {
            heartrate!!.registerSensor(true)
        } else if (action == MessageReceiverWear.HEART_RATE_STOP_SENSOR) {
            heartrate!!.registerSensor(false)
        }
    }

    private fun checkGyroscope(action: Int) {
        if (action == MessageReceiverWear.GYROSCOPE_START_SENSOR) {
            gyroscope!!.registerSensor(true)
        } else if (action == MessageReceiverWear.GYROSCOPE_STOP_SENSOR) {
            gyroscope!!.registerSensor(false)
        }
    }

    private fun checkAccelerometer(action: Int) {
        if (action == MessageReceiverWear.ACCELEROMETER_START_SENSOR) {
            accelerometer!!.registerSensor(true)
        } else if (action == MessageReceiverWear.ACCELEROMETER_STOP_SENSOR) {
            accelerometer!!.registerSensor(false)
        }
    }

    private fun checkStepCount(action: Int) {
        if (action == MessageReceiverWear.GET_STEP_COUNT) {
            steps!!.startSensor()
        }
    }
}