package com.damnluck.sensordatatransporterwearable.sensors

import android.content.Context
import android.os.AsyncTask
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.wearable.Node
import com.google.android.gms.wearable.Wearable


/***
 * This class helps to find the mobile (node).
 */
class FindSender internal constructor(private val nodesReady: NodesReady) :
    AsyncTask<Context?, Void?, List<Node>?>() {
    private fun find(context: Context): List<Node>? {
        try {
            val nodeListTask = Wearable.getNodeClient(context).connectedNodes
            val nodes = Tasks.await(nodeListTask)
            nodesReady.getNodes(nodes)
            return nodes
        } catch (e: Exception) {
            System.err.println("Exception$e")
        }
        return null
    }

    override fun doInBackground(vararg params: Context?): List<Node>? {
        return params[0]?.let { find(it) }
    }
}