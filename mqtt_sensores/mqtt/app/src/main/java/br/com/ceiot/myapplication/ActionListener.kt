package br.com.ceiot.myapplication

import android.util.Log
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttToken

/**
 * Criar classe  ActionListener que implementa IMqttActionListener
 * Esta classe é  utilizada por tratar mensagens de sucesso ou falha para a biblioteca Mqtt
 */
class ActionListener(private val name: String) : IMqttActionListener {

    override fun onSuccess(asyncActionToken: IMqttToken) {
        Log.d(TAG, "onSuccess: $name")
    }

    override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
        Log.d(TAG, "onFailure: $name")
    }

    companion object {

        private val TAG = "ActionListener"
    }
}
