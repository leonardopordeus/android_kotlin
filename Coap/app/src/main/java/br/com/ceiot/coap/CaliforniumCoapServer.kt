package br.com.ceiot.coap

import android.app.Service
import android.content.Intent
import android.os.IBinder
import org.eclipse.californium.core.CoapResource
import org.eclipse.californium.core.CoapServer
import org.eclipse.californium.core.server.resources.CoapExchange

/**
 * TODO (5) Implementação do Server como Service
 */
class CaliforniumCoapServer : Service() {

    internal var server: CoapServer? = null

    override fun onCreate() {
        this.server = CoapServer()
        server?.add(CoapServerResource("CEIOTCoapServer"))
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        server?.start()
        return Service.START_STICKY
    }

    override fun onDestroy() {
        server?.destroy()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

}
