package br.com.ceiot.aula09

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_node.*

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

        // Inserir dados por meio do contentProvider
        val contentValues = ContentValues()
        contentValues.put(NodeContract.Node.COLUMN_NAME, name.toString())
        contentValues.put(NodeContract.Node.COLUMN_DESCRIPTION, description.toString())

        val uri = contentResolver.insert(NodeContract.Node.CONTENT_URI, contentValues)

        if (uri != null) {
            Toast.makeText(baseContext, uri.toString(), Toast.LENGTH_LONG).show()
        }

        //8) TODO (8) Chama o serviço para salvar no firebase
        saveOnFirebase(name.toString(), description.toString())

        //Finaliza atividade e volta para a atividade principal.
        finish()
    }

    /**
     * 7) TODO (7) Monta intent para acionar o serviço para salvar no firebase
     * @param name
     * @param description
     */
    fun saveOnFirebase(name: String, description: String) {
        val sendDataIntentService = Intent(this, SendDataIntentService::class.java)
        sendDataIntentService.setAction(SendDataIntentService.ACTION_SEND_NODE_TO_FIREBASE)
        sendDataIntentService.putExtra(NodeContract.Node.COLUMN_NAME, name)
        sendDataIntentService.putExtra(NodeContract.Node.COLUMN_DESCRIPTION, description)
        startService(sendDataIntentService)
    }
}
