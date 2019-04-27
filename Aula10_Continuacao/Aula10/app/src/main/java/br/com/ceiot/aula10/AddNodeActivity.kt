package br.com.ceiot.aula10

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import br.com.ceiot.aula10.br.com.ceiot.aula10.entity.AppDatabase
import br.com.ceiot.aula10.br.com.ceiot.aula10.entity.Node
import kotlinx.android.synthetic.main.activity_add_node.*

/**
 * TODO (10) Criar uma segunda atividade para a tela de add um node.
 */
class AddNodeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_node)
    }

    /**
     * Salva o node.
     * @param view
     */
    fun onClickAddNode(view: View) {

        val name = name_editText.text
        val description = desc_editText.text

        if (name.length == 0) {
            Toast.makeText(this, "Name Empty", Toast.LENGTH_LONG)
            return
        } else if (description.length == 0) {
            Toast.makeText(this, "Description Empty", Toast.LENGTH_LONG)
            return
        }

        //TODO (11) Salva um item.
        val database = AppDatabase.getInstance(this.getApplication())
        if (database != null) {
            val node = Node(name.toString(),description.toString())
            AppExecutors.instance!!.diskIO().execute(Runnable {
                database.nodeDao().insertNode(node)
                saveOnFirebase(node.id,name.toString(),description.toString())
                finish()
            })
        }
    }

    /**
     * 7) TODO (18) Monta intent para acionar o serviço para salvar no firebase
     * @param name
     * @param description
     */
    fun saveOnFirebase(id:Int,name: String, description: String) {
        val sendDataIntentService = Intent(this, SendDataIntentService::class.java)
        sendDataIntentService.setAction(SendDataIntentService.ACTION_SEND_NODE_TO_FIREBASE)
        sendDataIntentService.putExtra(Node.COLUMN_ID, id)
        sendDataIntentService.putExtra(Node.COLUMN_NAME, name)
        sendDataIntentService.putExtra(Node.COLUMN_DESCRIPTION, description)
        startService(sendDataIntentService)
    }
}
