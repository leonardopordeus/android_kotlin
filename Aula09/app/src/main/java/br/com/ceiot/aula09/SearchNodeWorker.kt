package br.com.ceiot.aula09

import android.content.ContentValues
import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

/**
 * 11) TODO (11) Criar o serviço para ser executado periodicamente.
 * https://codelabs.developers.google.com/codelabs/android-workmanager-kt/#0
 */
class SearchNodeWorker (appContext: Context, workerParams: WorkerParameters)
    : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        try {
            val context = applicationContext

            val call = RestUtil().nodeService().list("nodes")
            val response = call.execute()
            val listNode: Map<String, NodeContract.Node>? = response.body()
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

    private fun createOutputData(listNode: Map<String, NodeContract.Node>?): Data {
        return Data.Builder()
            .putAll(listNode!!)
            .build()
    }

    private fun updateData(context: Context,listNode: Map<String, NodeContract.Node>?)
    {
        // Inserir dados por meio do contentProvider
        //context.getContentResolver().delete(NodeContract.Node.CONTENT_URI, null, null)
        if (listNode != null) {
            for (node in listNode) {
                try {
                    val contentValues = ContentValues()
                    contentValues.put(NodeContract.Node.COLUMN_NAME, node?.value.name)
                    contentValues.put(NodeContract.Node.COLUMN_DESCRIPTION, node?.value.description)
                    val uri = context.getContentResolver().insert(NodeContract.Node.CONTENT_URI, contentValues)
                }catch (e: java.lang.Exception)
                {
                    e.printStackTrace();
                }
            }
        }
    }

}