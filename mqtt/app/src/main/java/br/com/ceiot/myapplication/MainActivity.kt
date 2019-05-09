package br.com.ceiot.myapplication

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage
import java.io.UnsupportedEncodingException

class MainActivity : AppCompatActivity() {

    var mqttClient: AndroidMqttClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO (7) Inicializar MQTT Client
        try {
            this.mqttClient = AndroidMqttClient(
                this,
                "iot.eclipse.org",
                "1883",
                MqttCallBackActivity(this, "MainActivityMqttCallback")
            )
            val token = this.mqttClient!!.connect()

        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    /**
     * TODO (8) Implementar publish
     * @param view
     */
    fun publish(view: View) {
        val topic = editText_publish_topic.getText().toString()
        val message = editText_publish_msg.getText().toString()
        try {
            mqttClient!!.publishMessage(message, 0, topic)
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        } catch (e: MqttException) {
            e.printStackTrace()
        }

    }

    /**
     * TODO (9) Implementar subscribe
     * @param view
     */
    fun subscribe(view: View) {
        val topic = editText_subscribe_topic.getText().toString()
        try {
            mqttClient!!.subscribe(topic, 0)
        } catch (e: MqttException) {
            e.printStackTrace()
        }

    }

    /**
     * TODO (10) Implementar unsubscribe
     * @param view
     */
    fun unsubscribe(view: View) {
        val topic = editText_subscribe_topic.getText().toString()
        try {
            mqttClient!!.unSubscribe(topic)
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }


    /**
     * TODO (11) Estender MqttCallBackActivity para tratar mensagens recebidas na MainActivity
     */
    inner class MqttCallBackActivity(context: Context, clientHandle: String) :
        MqttCallbackHandler(context, clientHandle) {
        @Throws(Exception::class)
        override fun messageArrived(topic: String, message: MqttMessage) {
            super.messageArrived(topic, message)
            textView_result.setText("$topic:$message")
        }
    }
}
