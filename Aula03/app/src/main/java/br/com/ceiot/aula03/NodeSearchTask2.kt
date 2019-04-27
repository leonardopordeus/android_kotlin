package br.com.ceiot.aula03

import android.os.AsyncTask
import kotlinx.android.synthetic.main.activity_main.*

class NodeSearchTask2 (private var activity: MainActivity) : AsyncTask<String, Void, Map<String, Node>>()
{
    override fun doInBackground(vararg params: String?): Map<String, Node> {
        //TODO (7): Cria a url para fazer a requisição para o serviço
        val call = RestUtil().nodeService().list("nodes")
        val response = call.execute()
        val listNode:Map<String,Node>? = response.body()
        return listNode
    }
}