package br.com.ceiot.aula03

import android.os.AsyncTask
import android.widget.Toast

/**
 * TODO(13) Criação da tarefa para salvar um novo item.
 */
class NodeCreateTask (private var activity: MainActivity) : AsyncTask<String, Void, String>()
{
    override fun doInBackground(vararg params: String?): String {
        var node:Node = Node ("ABC","YYY","ZZZ")
        val call = RestUtil().nodeService().saveOnFirebase("nodes",node)
        val response = call.execute()
        val result:Map<String,String>? = response.body()
        return "OK"
    }

    override fun onPostExecute(result: String) {
        Toast.makeText(activity, result, Toast.LENGTH_SHORT).show()
    }
}