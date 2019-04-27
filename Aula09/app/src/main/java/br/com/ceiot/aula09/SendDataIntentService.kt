package br.com.ceiot.aula09

import android.app.IntentService
import android.content.Intent
import android.content.Context



//// TODO: Rename parameters
//private const val EXTRA_PARAM1 = "br.com.ceiot.aula09.extra.PARAM1"
//private const val EXTRA_PARAM2 = "br.com.ceiot.aula09.extra.PARAM2"

/**
 * 1) TODO (1) Criar um serviço para enviar dados para o firebase.
 *
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
class SendDataIntentService : IntentService("SendDataIntentService") {

    //3) TODO (3) Trata recebimento da Intent
    override fun onHandleIntent(intent: Intent?) {
        when (intent?.action) {
            ACTION_SEND_NODE_TO_FIREBASE -> {
                val name = intent.getStringExtra(NodeContract.Node.COLUMN_NAME)
                val description = intent.getStringExtra(NodeContract.Node.COLUMN_DESCRIPTION)
                handleActionSendNode(this, name, description)
            }
            ACTION_SEARCH_ON_FIREBASE -> {
                val name = intent.getStringExtra(NodeContract.Node.COLUMN_NAME)
                val description = intent.getStringExtra(NodeContract.Node.COLUMN_DESCRIPTION)
                handleActionSearchNode(this, name, description)
            }
        }
    }

    /**
     * 4) TODO (4)  Envia Node para o Firebase
     */
    private fun handleActionSendNode(context: Context, name: String, description: String) {
        var node:NodeContract.Node = NodeContract.Node(name, description,"")
        val call = RestUtil().nodeService().saveOnFirebase("nodes",node)
        val response = call.execute()
        val result:Map<String,String>? = response.body()

        var notificationUtil:NotificationUtil = NotificationUtil()
        notificationUtil.notification(context, "Node inserted")
    }

    /**
     * 5) TODO (5)  Busca Node do Firebase
     */
    private fun handleActionSearchNode(context: Context, name: String, description: String) {
        val call = RestUtil().nodeService().list("nodes")
        val response = call.execute()
        val listNode:Map<String,NodeContract.Node>? = response.body()
        //Notifica
        var notificationUtil:NotificationUtil = NotificationUtil()
        notificationUtil.notification(context, "Nodes updated")
    }

    companion object {
        // 2) TODO (2): Rename actions, choose action names that describe tasks that this
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
