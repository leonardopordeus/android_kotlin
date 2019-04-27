package br.com.ceiot.aula10

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

/**
 * TODO (18) Exemplo de notificação
 * Esta classe implementa rotinas para envio de notificações.
 *
 * https://developer.android.com/training/notify-user/build-notification.html
 */
class NotificationUtil {

    private val NOTIFICATION_CHANNEL_ID = "notification.channel"
    private val APPCEIOT_NOTIFICATION_CHANNEL_ID = 1199
    private val ACTION_CLEAR_NOTIF = 1

    /**
     *
     * @param context
     * @param msg
     */
    fun notification(context: Context, msg: String) {
        //Recupera Serviço para notificação
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //Cria um canal para envio de notificações.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            val mChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Primary",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(mChannel)
        }
        //Constroi a notificação
        val notificationBuilder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
            .setSmallIcon(R.drawable.ic_launcher_background)
//            .setLargeIcon(largeIcon(context))
            .setContentTitle("Notification Example")
            .setContentText(msg)
            .setStyle(NotificationCompat.BigTextStyle().bigText("Style Example"))
            .setDefaults(Notification.DEFAULT_VIBRATE)
            .setContentIntent(contentIntent(context))//Ao clicar vai para a atividade inicial
            .addAction(ignoreAction(context))//Opções
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.priority = NotificationCompat.PRIORITY_HIGH
        }
        notificationManager.notify(APPCEIOT_NOTIFICATION_CHANNEL_ID, notificationBuilder.build())
    }

    /*
        Recupera icone
     */
    private fun largeIcon(context: Context): Bitmap {
        val res = context.resources
        val largeIcon:Bitmap = BitmapFactory.decodeResource(res, R.drawable.ic_launcher_background)
        return largeIcon
    }

    private fun contentIntent(context: Context): PendingIntent {
        val startActivityIntent = Intent(context, MainActivity::class.java)
        return PendingIntent.getActivity(
            context,
            APPCEIOT_NOTIFICATION_CHANNEL_ID,
            startActivityIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun ignoreAction(context: Context): NotificationCompat.Action {
        val ignoreIntent = Intent(context, SendDataIntentService::class.java)
        ignoreIntent.action = SendDataIntentService.ACTION_DO_NOTHING
        val ignorePendingIntent = PendingIntent.getService(
            context,
            ACTION_CLEAR_NOTIF,
            ignoreIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        return NotificationCompat.Action(
            R.drawable.ic_launcher_background,
            "Ignore",
            ignorePendingIntent
        )
    }

    fun clearAllNotifications(context: Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancelAll()
    }
}