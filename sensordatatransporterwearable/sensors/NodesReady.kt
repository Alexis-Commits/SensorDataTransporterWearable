package com.damnluck.sensordatatransporterwearable.sensors

import com.google.android.gms.wearable.Node

/**
 * This interface can be implemented to listen when a node (mobile) is found.
 */
interface NodesReady {
    fun getNodes(nodes: List<Node?>?)
}