package br.com.ceiot.aula10

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * TODO (34) Configurar um BroadcastReceiver
 */
class ChargingBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent?.action) {
            Intent.ACTION_POWER_CONNECTED -> notif(context,"CONNECTED")
            Intent.ACTION_POWER_DISCONNECTED -> notif(context,"DISCONNECTED")
        }
    }

    fun notif(context: Context, message:String)
    {
        var notification:NotificationUtil = NotificationUtil()
        notification.notification(context, message)
    }
}
