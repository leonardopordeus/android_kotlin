package br.com.ceiot.myapplication

import android.content.Context
import android.util.Log
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended
import org.eclipse.paho.client.mqttv3.MqttMessage

/**
 * TODO (5) Criar classe  MqttCallbackHandler que implementa MqttCallbackExtended
 * Esta classe é  utilizada por tratar mensagens de conexão, conexão perdida, Mensagem enviada/recebida
 */
open class MqttCallbackHandler(private val context: Context, private val clientHandle: String) : MqttCallbackExtended {

    override fun connectComplete(reconnect: Boolean, serverURI: String) {
        Log.d(TAG, "connectComplete: $clientHandle")
    }

    override fun connectionLost(cause: Throwable) {
        Log.d(TAG, "connectionLost: $clientHandle")
    }

    @Throws(Exception::class)
    override fun messageArrived(topic: String, message: MqttMessage) {
        Log.d(TAG, "messageArrived: $clientHandle")

    }

    override fun deliveryComplete(token: IMqttDeliveryToken) {
        Log.d(TAG, "deliveryComplete: $clientHandle")
    }

    companion object {
        private val TAG = "MqttCallbackHandler"
    }
}
