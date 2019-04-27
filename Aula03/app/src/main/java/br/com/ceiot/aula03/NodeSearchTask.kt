package br.com.ceiot.aula03

import android.os.AsyncTask
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

/**
 * TODO (6) Criação da tarefa em paralelo
 */
class NodeSearchTask (private var activity: MainActivity) : AsyncTask<String,Void,Map<String,Node>>()
{
    override fun doInBackground(vararg params: String) : Map<String,Node>? {
        //TODO (7): Cria a url para fazer a requisição para o serviço
        val call = RestUtil().nodeService().list("nodes")
        val response = call.execute()
        val listNode:Map<String,Node>? = response.body()
        return listNode
    }

    override fun onPostExecute(listNodes:Map<String,Node>?)
    {
        //TODO (8): Preenche lista
        var adapter: ArrayAdapter<String> = activity.list_view.adapter as ArrayAdapter<String>
        adapter.clear()
        if (listNodes != null) {
            for (node in listNodes) {
                try {
                    var value:String = node?.value?.name
                    if(value != null) {
                        adapter.add(value);
                    }
                }catch (e:Exception)
                {
                    e.printStackTrace();
                }
            }
        }
    }

}
