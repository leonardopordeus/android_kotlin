package br.com.ceiot.myapplication

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage
import java.io.UnsupportedEncodingException

class MainActivity : AppCompatActivity(), SensorEventListener {

    var mqttClient: AndroidMqttClient? = null

    //TODO (1) Declarar gerenciador
    private var mSensorManager: SensorManager? = null
    private var mSensor: Sensor? = null
    private var mSensorLight: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Inicializar MQTT Client
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

        //TODO (2) Verificar a existencia de um sensor
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

//        var sensorList:StringBuilder = StringBuilder()
//        var list:List<Sensor> = mSensorManager!!.getSensorList(Sensor.TYPE_ALL);
//        for(sensor in list){
//            sensorList.append(sensor.toString()).append("\r\n");
//        }
//        textView_result.text = sensorList;

        if (mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            // Success!
            mSensor = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        } else {
            mSensor = null
        }

        if (mSensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT) != null) {
            // Success!
            mSensorLight = mSensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)
        } else {
            mSensorLight = null
        }
    }

    //Registro dos sensores
    override fun onResume() {
        super.onResume()
        mSensorManager!!.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL)
        mSensorManager!!.registerListener(this, mSensorLight, SensorManager.SENSOR_DELAY_NORMAL)
    }

    //Desregistrar sensores
    override fun onPause() {
        super.onPause()
        mSensorManager!!.unregisterListener(this)
    }

    /**
     * Implementar publish
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
     * Implementar subscribe
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
     * Implementar unsubscribe
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

    override fun onSensorChanged(event: SensorEvent) {
        //TODO (4) Recupera valor
        if (Sensor.TYPE_ACCELEROMETER == event.sensor.type) {
            var ACCELEROMETER = ""
            ACCELEROMETER = ACCELEROMETER + "x axis: " + event.values[0] + "\r\n"
            ACCELEROMETER = ACCELEROMETER + "y axis: " + event.values[1] + "\r\n"
            ACCELEROMETER = ACCELEROMETER + "z axis: " + event.values[2] + "\r\n"
            textView_result.text = ACCELEROMETER
        } else if (Sensor.TYPE_LIGHT == event.sensor.type) {
            val LIGHT = "Lux: " + event.values[0] + "\r\n"
            textView_result2.setText(LIGHT)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // Do something here if sensor accuracy changes.
    }


    /**
     * Estender MqttCallBackActivity para tratar mensagens recebidas na MainActivity
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
