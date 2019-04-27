package br.com.ceiot.aula07

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private val callbacks = ArrayList<String>()

    /**
     * TODO (1) Declaração das constates utilizadas
     */
    companion object {
        //TAG utilizada para log.
        private val TAG = MainActivity::class.java.simpleName
        //Constante utilizada comon referência para o Bundle.
        val CEIOT_CALLBACKS_TEXT_KEY = "callbacks"
        //Constantes para logs dos respectivos eventos
        private val ON_CREATE = "onCreate"
        private val ON_START = "onStart"
        private val ON_RESUME = "onResume"
        private val ON_PAUSE = "onPause"
        private val ON_STOP = "onStop"
        private val ON_RESTART = "onRestart"
        private val ON_DESTROY = "onDestroy"
        private val ON_SAVE_INSTANCE_STATE = "onSaveInstanceState"
    }

    /**
     * TODO (2) Função para Log e append
     * Logs to the console and appends the lifecycle method name to the TextView so that you can
     * view the series of method callbacks that are called both from the app and from within
     * Android Studio's Logcat.
     *
     * @param lifecycleEvent The name of the event to be logged.
     */
    private fun logAndAppend(lifecycleEvent: String) {
        Log.d(TAG, "Lifecycle Event: $lifecycleEvent")
        textView.append(lifecycleEvent + "\n")
    }

    /*
       TODO(3) Preenche todo os métodos do ciclo de vida.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO (5) Recupera valores de eventos da lista de "callbacks"
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(CEIOT_CALLBACKS_TEXT_KEY)) {
                val previousEvents = savedInstanceState
                    .getString(CEIOT_CALLBACKS_TEXT_KEY)
                textView.text = previousEvents
            }
        }

        for (i in callbacks.indices.reversed()) {
            textView.append(callbacks[i] + "\n")
        }
        //Limpa a lista
        callbacks.clear()

        logAndAppend(ON_CREATE)
    }

    override fun onStart() {
        super.onStart()
        logAndAppend(ON_START)
    }

    override fun onResume() {
        super.onResume()
        logAndAppend(ON_RESUME)
    }

    override fun onPause() {
        super.onPause()
        logAndAppend(ON_PAUSE)
    }

    override fun onStop() {
        super.onStop()
        logAndAppend(ON_STOP)

        //TODO (6) Adiciona o evento ON_STOP
        callbacks.add(0, ON_STOP)
    }

    override fun onRestart() {
        super.onRestart()
        logAndAppend(ON_RESTART)
    }

    override fun onDestroy() {
        super.onDestroy()
        logAndAppend(ON_DESTROY)

        //TODO (7) Adiciona o evento ON_DESTROY
        callbacks.add(0, ON_DESTROY)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        logAndAppend(ON_SAVE_INSTANCE_STATE)

        //TODO (8) Salva eventos
        val oldLogs = textView.text.toString()
        outState.putString(CEIOT_CALLBACKS_TEXT_KEY, oldLogs)
    }
}
