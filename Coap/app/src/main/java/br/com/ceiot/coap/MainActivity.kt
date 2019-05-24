package br.com.ceiot.coap

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.eclipse.californium.core.CoapHandler
import org.eclipse.californium.core.CoapResponse

class MainActivity : AppCompatActivity() {

    //TODO (7) Criação das entiddades que serão utilizadas para o COAP
    internal var californiumSimpleClient: CaliforniumSimpleClient = CaliforniumSimpleClient();
    internal var mainCoapHandler: MainCoapHandler = MainCoapHandler();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startService(Intent(this, CaliforniumCoapServer::class.java))
    }

    override fun onDestroy() {
        stopService(Intent(this, CaliforniumCoapServer::class.java))
        super.onDestroy()
    }

    /**
     * TODO (8)
     * Método que irá implementar a ação do botão em tela para realizar um GET
     * @param view
     */
    fun getCoap(view: View) {
        val topic = editText_publish_topic.getText().toString()
        AppExecutors.instance!!.networkIO().execute(Runnable {
            var result = californiumSimpleClient.get(topic)
            AppExecutors.instance!!.mainThread().execute(Runnable {
                textView_result.text = result
            })
        })
    }

    /**
     * TODO (9)
     * Método que irá implementar a ação do botão em tela para realizar um discovery
     * @param view
     */
    fun discovery(view: View) {
        val topic = editText_publish_topic.getText().toString()
        //CoapDiscoveryTask().execute(topic)
        AppExecutors.instance!!.networkIO().execute(Runnable {
            var result = californiumSimpleClient.discovery(topic)
            AppExecutors.instance!!.mainThread().execute(Runnable {
                textView_result.text = result
            })
        })
    }

    /**
     * TODO (10)
     * Método que irá implementar a ação do botão em tela para realizar um Observer
     * @param view
     */
    fun observe(view: View) {
        val topic = editText_publish_topic.getText().toString()
        AppExecutors.instance!!.networkIO().execute(Runnable {
            californiumSimpleClient.observe(topic, mainCoapHandler);
        })
    }

    /**
     * TODO (11)
     * Tratamento da resposta do observer.
     */
    internal inner class MainCoapHandler : CoapHandler {

        override fun onLoad(response: CoapResponse) {
            textView_result.text = response.responseText
        }

        override fun onError() {
            textView_result.text = "onError"
        }
    }
}
