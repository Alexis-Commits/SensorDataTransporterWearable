package com.damnluck.sensordatatransporterwearable.models

/**
* This object holds the values of the sensors. X Y Z of Gyroscope or Accelerometer.
* */
class XYZ(val x: Float, val y: Float, val z: Float) {

    override fun toString(): String {
        return "$x,$y,$z"
    }
}