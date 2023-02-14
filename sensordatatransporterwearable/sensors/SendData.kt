package com.damnluck.sensordatatransporterwearable.sensors

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.wearable.Node
import com.google.android.gms.wearable.Wearable
import java.lang.ref.WeakReference
import java.util.concurrent.ExecutionException

/**
 * This class keeps the logic of sending any kind of data to a node (mobile).
 * It uses AsyncTask instead of Thread because AsyncTask respects the line of async operations.
 * FIFO
 */
class SendData internal constructor(
    context: Context,
    private val path: String,
    private val message: String,
    nodes: List<Node>
) :
    AsyncTask<Void?, Void?, Void?>() {
    private val wr: WeakReference<SendData>
    private val c: WeakReference<Context>
    private val nodes: List<Node>

    init {
        c = WeakReference(context.applicationContext)
        wr = WeakReference(this)
        this.nodes = nodes
    }

    override fun doInBackground(vararg voids: Void?): Void? {
        send()
        return null
    }

    override fun onPostExecute(aVoid: Void?) {
        wr.clear()
        c.clear()
        cancel(true)
    }

    private fun sendMessage(node: Node) {
        val sendMessageTask =
            c.get()
                ?.let {
                    Wearable.getMessageClient(it).sendMessage(node.id, path, message.toByteArray())
                }
        try {
            Tasks.await(sendMessageTask!!)
        } catch (exception: ExecutionException) {
            Log.e("SensorDataTransporter", "Task failed: $exception")
        } catch (exception: InterruptedException) {
            Log.e("SensorDataTransporter", "Interrupt occurred: $exception")
        }
    }

    private fun send() {
        try {
            for (node in nodes) {
                sendMessage(node)
            }
        } catch (exception: Exception) {
            Log.e("SensorDataTransporter", "Task failed: $exception")
        }
    }
}