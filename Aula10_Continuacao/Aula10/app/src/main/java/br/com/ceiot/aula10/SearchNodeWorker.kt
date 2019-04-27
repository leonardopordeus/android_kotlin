package br.com.ceiot.aula10

import android.content.ContentValues
import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import br.com.ceiot.aula10.br.com.ceiot.aula10.entity.AppDatabase
import br.com.ceiot.aula10.br.com.ceiot.aula10.entity.Node

/**
 * 11) TODO (18) Criar o serviço para ser executado periodicamente.
 * https://codelabs.developers.google.com/codelabs/android-workmanager-kt/#0
 */
class SearchNodeWorker (appContext: Context, workerParams: WorkerParameters)
    : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        try {
            val context = applicationContext

            val call = RestUtil().nodeService().list("nodes")
            val response = call.execute()
            val listNode: Map<String, Node>? = response.body()
            //Notifica
            var notificationUtil: NotificationUtil = NotificationUtil()
            notificationUtil.notification(context, "Nodes updated")

            updateData(context,listNode)
//            val outputData = createOutputData(listNode)

        }catch (e: Exception) {
            e.printStackTrace()
        }
        return Result.success()
    }

    private fun createOutputData(listNode: Map<String, Node>?): Data {
        return Data.Builder()
            .putAll(listNode!!)
            .build()
    }

    private fun updateData(context: Context,listNode: Map<String, Node>?)
    {
        // Inserir dados por meio do contentProvider
        val database = AppDatabase.getInstance(context)
        if (listNode != null) {
            database!!.nodeDao().deleteAll()
            for (node in listNode) {
                try {
                    val contentValues = ContentValues()
                    database.nodeDao().insertNode(node.value)
                }catch (e: java.lang.Exception)
                {
                    e.printStackTrace();
                }
            }
        }
    }

}