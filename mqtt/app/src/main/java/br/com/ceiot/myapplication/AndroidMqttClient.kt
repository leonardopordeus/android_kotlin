package br.com.ceiot.myapplication

import android.content.Context
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

import java.io.UnsupportedEncodingException

/**
 * TODO (6) Criar classe para unificar métodos relacionados ao MQTT
 */
class AndroidMqttClient {

    private var mqttClient: MqttAndroidClient? = null
    private var brokerURL: String? = null
    private var brokerPort: String? = null
    internal var context: Context

    /**
     * Construtora que inicia serviço para um determinado broker.
     * @param context
     * @param brokerURL
     * @param brokerPort
     */
    internal constructor(context: Context, brokerURL: String, brokerPort: String) {
        this.context = context
        this.brokerURL = brokerURL
        this.brokerPort = brokerPort
        createMqttClient(MqttCallbackHandler(this.context.applicationContext, "AppCEIOT Callback"))
    }

    internal constructor(context: Context, brokerURL: String, brokerPort: String, mqttCallback: MqttCallback) {
        this.context = context
        this.brokerURL = brokerURL
        this.brokerPort = brokerPort
        createMqttClient(mqttCallback)
    }

    /**
     * Inicializa client.
     * @return
     */
    fun createMqttClient(mqttCallback: MqttCallback): MqttAndroidClient {
        val clientId = MqttClient.generateClientId()
        this.mqttClient = MqttAndroidClient(
            this.context.applicationContext,
            "tcp://" + this.brokerURL + ":" + this.brokerPort,
            clientId
        )
        this.mqttClient!!.setCallback(mqttCallback)
        return this.mqttClient!!
    }

    /**
     * Realiza conexão
     * @return
     * @throws MqttException
     */
    @Throws(MqttException::class)
    fun connect(): IMqttToken {
        val options = MqttConnectOptions()
        options.mqttVersion = MqttConnectOptions.MQTT_VERSION_3_1
        options.isAutomaticReconnect = true
        val token = mqttClient!!.connect(options)
        token.actionCallback = ActionListener("MqttConnect")
        return token
    }

    /**
     * Implementa desconexão
     * @throws MqttException
     */
    @Throws(MqttException::class)
    fun disconnect() {
        val mqttToken = mqttClient!!.disconnect()
        mqttToken.actionCallback = ActionListener("MqttDisconnect")
    }

    /**
     * Publica uma mensagem no broker
     * @param message
     * @param qos
     * @param topic
     * @throws MqttException
     * @throws UnsupportedEncodingException
     */
    @Throws(MqttException::class, UnsupportedEncodingException::class)
    fun publishMessage(message: String, qos: Int, topic: String) {
        var encodedPayload = ByteArray(0)
        encodedPayload = message.toByteArray(charset("UTF-8"))
        val encodedMessage = MqttMessage(encodedPayload)
        mqttClient!!.publish(topic, encodedMessage)
    }

    /**
     * Se inscreve para escutar um determinado tópico
     * @param topic
     * @param qos
     * @throws MqttException
     */
    @Throws(MqttException::class)
    fun subscribe(topic: String, qos: Int) {
        val token = mqttClient!!.subscribe(topic, qos)
        token.actionCallback = ActionListener("MqttSubscribe")
    }

    /**
     * Cancela inscrição em um determinado tópico
     * @param topic
     * @throws MqttException
     */
    @Throws(MqttException::class)
    fun unSubscribe(topic: String) {
        val token = mqttClient!!.unsubscribe(topic)
        token.actionCallback = ActionListener("MqttUnSubscribe")
    }

    companion object {

        private val TAG = "AndroidMqttClient"
    }

}
