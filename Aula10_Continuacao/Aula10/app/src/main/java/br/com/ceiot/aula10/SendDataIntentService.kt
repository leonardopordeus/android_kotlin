package br.com.ceiot.aula10

import android.app.IntentService
import android.content.Intent
import android.content.Context
import br.com.ceiot.aula10.br.com.ceiot.aula10.entity.Node


//// TODO: Rename parameters
//private const val EXTRA_PARAM1 = "br.com.ceiot.aula09.extra.PARAM1"
//private const val EXTRA_PARAM2 = "br.com.ceiot.aula09.extra.PARAM2"

/**
 * 1) TODO (13) Criar um serviço para enviar dados para o firebase.
 *
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
class SendDataIntentService : IntentService("SendDataIntentService") {

    //3) TODO (15) Trata recebimento da Intent
    override fun onHandleIntent(intent: Intent?) {
        when (intent?.action) {
            ACTION_SEND_NODE_TO_FIREBASE -> {
                val id = intent.getIntExtra(Node.COLUMN_ID,0)
                val name = intent.getStringExtra(Node.COLUMN_NAME)
                val description = intent.getStringExtra(Node.COLUMN_DESCRIPTION)
                handleActionSendNode(this, id, name, description)
            }
            ACTION_SEARCH_ON_FIREBASE -> {
                val id = intent.getIntExtra(Node.COLUMN_ID,0)
                val name = intent.getStringExtra(Node.COLUMN_NAME)
                val description = intent.getStringExtra(Node.COLUMN_DESCRIPTION)
                handleActionSearchNode(this, name, description)
            }
        }
    }

    /**
     * 4) TODO (16)  Envia Node para o Firebase
     */
    private fun handleActionSendNode(context: Context,id:Int, name: String, description: String) {
        var node:Node = Node(id,name, description)
        val call = RestUtil().nodeService().saveOnFirebase("nodes",node)
        val response = call.execute()
        val result:Map<String,String>? = response.body()

        var notificationUtil:NotificationUtil = NotificationUtil()
        notificationUtil.notification(context, "Node inserted")
    }

    /**
     * 5) TODO (17)  Busca Node do Firebase
     */
    private fun handleActionSearchNode(context: Context, name: String, description: String) {
        val call = RestUtil().nodeService().list("nodes")
        val response = call.execute()
        val listNode:Map<String,Node>? = response.body()
        //Notifica
        var notificationUtil:NotificationUtil = NotificationUtil()
        notificationUtil.notification(context, "Nodes updated")
    }

    companion object {
        // 2) TODO (14): Rename actions, choose action names that describe tasks that this
        // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
        public const val ACTION_SEND_NODE_TO_FIREBASE = "br.com.ceiot.aula09.action.send.node.to.firebase"
        public const val ACTION_SEARCH_ON_FIREBASE = "br.com.ceiot.aula09.action.search.on.firebase"
        public const val ACTION_DO_NOTHING = "br.com.ceiot.aula09.action.do.nothing"
//        /**
//         * Starts this service to perform action Foo with the given parameters. If
//         * the service is already performing a task this action will be queued.
//         *
//         * @see IntentService
//         */
//        // TODO: Customize helper method
//        @JvmStatic
//        fun startActionFoo(context: Context, param1: String, param2: String) {
//            val intent = Intent(context, SendDataIntentService::class.java).apply {
//                action = ACTION_FOO
//                putExtra(EXTRA_PARAM1, param1)
//                putExtra(EXTRA_PARAM2, param2)
//            }
//            context.startService(intent)
//        }
//
//        /**
//         * Starts this service to perform action Baz with the given parameters. If
//         * the service is already performing a task this action will be queued.
//         *
//         * @see IntentService
//         */
//        // TODO: Customize helper method
//        @JvmStatic
//        fun startActionBaz(context: Context, param1: String, param2: String) {
//            val intent = Intent(context, SendDataIntentService::class.java).apply {
//                action = ACTION_BAZ
//                putExtra(EXTRA_PARAM1, param1)
//                putExtra(EXTRA_PARAM2, param2)
//            }
//            context.startService(intent)
//        }
    }
}
