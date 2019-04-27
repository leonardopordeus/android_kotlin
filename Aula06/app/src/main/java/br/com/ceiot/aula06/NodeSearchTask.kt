package br.com.ceiot.aula06

import android.os.AsyncTask
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

/**
 * Criação da tarefa em paralelo
 */
class NodeSearchTask (private var activity: MainActivity) : AsyncTask<String,Void,Map<String,Node>>()
{
    override fun doInBackground(vararg params: String) : Map<String,Node>? {
        //Cria a url para fazer a requisição para o serviço
        val call = RestUtil().nodeService().list("nodes")
        val response = call.execute()
        val listNode:Map<String,Node>? = response.body()
        return listNode
    }


    override fun onPostExecute(listNodes:Map<String,Node>?)
    {
        /*
            Preenche lista
            Alterar código para usar o NodeAdapter
         */
        var adapter: NodeAdapter = activity.my_recycler_view.adapter as NodeAdapter
        adapter.resetList()
        if (listNodes != null) {
            for (node in listNodes) {
                try {
                    adapter.adicionarItem(node?.value)
                }catch (e:Exception)
                {
                    e.printStackTrace();
                }
            }
        }
    }

}
