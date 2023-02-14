package com.damnluck.sensordatatransporterwearable.core

import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService

/**
 * This Receiver Listens to messages from the mobile device.
 * It runs at background so it needs a Broadcast sender to inform app about new messages.
 */
class MessageReceiverWear : WearableListenerService() {
    private var path = ""
    override fun onMessageReceived(messageEvent: MessageEvent) {
        path = messageEvent.path
        checkHeartRate()
        checkGyroscope()
        checkAccelerometer()
        checkStepCount()
    }

    private fun checkStepCount() {
        if (path == "/getStepCount") {
            sendBroadcast(GET_STEP_COUNT)
        }
    }

    private fun checkHeartRate() {
        if (path == "/startHeartRate") {
            sendBroadcast(HEART_RATE_START_SENSOR)
        } else if (path == "/stopHeartRate") {
            sendBroadcast(HEART_RATE_STOP_SENSOR)
        }
    }

    private fun checkGyroscope() {
        if (path == "/startGyroscope") {
            sendBroadcast(GYROSCOPE_START_SENSOR)
        } else if (path == "/stopGyroscope") {
            sendBroadcast(GYROSCOPE_STOP_SENSOR)
        }
    }

    private fun checkAccelerometer() {
        if (path == "/startAccelerometer") {
            sendBroadcast(ACCELEROMETER_START_SENSOR)
        } else if (path == "/stopAccelerometer") {
            sendBroadcast(ACCELEROMETER_STOP_SENSOR)
        }
    }

    /**
     * This function send a broadcast to the App.
     */
    private fun sendBroadcast(type: Int) {
        val messageIntent = Intent()
        messageIntent.action = Intent.ACTION_SEND
        messageIntent.putExtra("action", type.toString())
        LocalBroadcastManager.getInstance(this).sendBroadcast(messageIntent)
    }

    companion object {
        var HEART_RATE_START_SENSOR = 0
        var HEART_RATE_STOP_SENSOR = 1
        var GYROSCOPE_STOP_SENSOR = 2
        var GYROSCOPE_START_SENSOR = 3
        var ACCELEROMETER_STOP_SENSOR = 4
        var ACCELEROMETER_START_SENSOR = 5
        var GET_STEP_COUNT = 6
    }
}