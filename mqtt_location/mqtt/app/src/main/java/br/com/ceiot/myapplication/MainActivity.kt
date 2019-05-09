package br.com.ceiot.myapplication

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.android.synthetic.main.activity_main.*
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage
import java.io.UnsupportedEncodingException

class MainActivity : AppCompatActivity() {

    var mqttClient: AndroidMqttClient? = null

    //TODO (2) Declaração dos componentes para trabalhar com localização
    private val TAG = "MainActivity"
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var mLocationCallback: LocationCallback? = null
    private var mLocationRequest: LocationRequest? = null
    private var mSettingsClient: SettingsClient? = null
    private var mLocationSettingsRequest: LocationSettingsRequest? = null

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

        //TODO (3) Inicializa LocationServices
        // Recupera a última localização conhecida
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mSettingsClient = LocationServices.getSettingsClient(this)

        //TODO (11) Configurar serviço de localização
        createLocationCallback()
        createLocationRequest()
        buildLocationSettingsRequest()
    }

    override fun onResume() {
        super.onResume()
        // TODO (4) Verifica permissões do usuário
        if (checkPermissions()) {
            getLastLocation();
            //TODO (12) Iniciar serviço de atualização de localização
            startLocationUpdates()
        }
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    /**
     * TODO (5) Retorna o status de permissão para localização
     * @return
     */
    private fun checkPermissions(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if(permissionState == PackageManager.PERMISSION_GRANTED)
        {
            return true
        }
        Toast.makeText(this, "Habilitar permissões", Toast.LENGTH_LONG).show();
        return false
    }

    //TODO (6) Recupera localização
    private fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            Toast.makeText(this, "Habilitar permissões", Toast.LENGTH_LONG).show();
            return;
        }
        mFusedLocationClient!!.getLastLocation()
            .addOnSuccessListener(this) { location ->
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    textView_result.text = location.toString()
                }
            }
    }

    /**
     * //TODO (7) Criar callback da atualização da localização
     * Creates a callback for receiving location events.
     */
    fun createLocationCallback() {
        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)
                if (locationResult == null) {
                    return
                }
                val locationStr = StringBuilder()
                for (location in locationResult.locations) {
                    locationStr.append(location.toString() + "\r\n")
                }
                textView_result.text = locationStr.toString()
            }
        }
    }

    /**
     * TODO (8) Configura requests para localização
     */
    protected fun createLocationRequest() {
        mLocationRequest = LocationRequest.create()
        mLocationRequest!!.setInterval(5000)
        mLocationRequest!!.setFastestInterval(1000)
        mLocationRequest!!.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)

    }

    /**
     * TODO (9) Inicia configurações
     */
    private fun buildLocationSettingsRequest() {
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest!!)
        mLocationSettingsRequest = builder.build()
    }

    /**
     * TODO (10) Cadastra para escutar alterações na localização
     */
    private fun startLocationUpdates() {
        // Begin by checking if the device has the necessary location settings.
        if (ActivityCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            Toast.makeText(this, "Habilitar permissões", Toast.LENGTH_LONG).show();
            return;
        }

        mSettingsClient!!.checkLocationSettings(mLocationSettingsRequest)
            .addOnSuccessListener(this) {
                Log.i(TAG, "All location settings are satisfied.")

                mFusedLocationClient!!.requestLocationUpdates(
                    mLocationRequest,
                    mLocationCallback, Looper.myLooper()
                )
            }
            .addOnFailureListener(
                this
            ) { Log.i(TAG, "addOnFailureListener") }
    }

    /**
     * TODO (13) Remover serviço de atualziação
     */
    private fun stopLocationUpdates() {
        mFusedLocationClient!!.removeLocationUpdates(mLocationCallback)
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
