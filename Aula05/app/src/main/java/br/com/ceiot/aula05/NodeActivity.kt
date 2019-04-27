package br.com.ceiot.aula05

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_node.*

/**
 * TODO (0) Para criar uma nova activity:
 *  1) Clicar com o botão direito nas pastas que contém código
 *  2) New => Activity => EmptyActivity
 *  3) Activity Name: NodeActivity
 *     Layout name: activity_node
 */
class NodeActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_node)

        //TODO (3) Recuperar informações enviadas

        val intent = intent

        if (intent.hasExtra(Node.NODE_NOME)) {
            val nome = intent.getStringExtra(Node.NODE_NOME)
            node_nome.setText(nome)
        }
        if (intent.hasExtra(Node.NODE_LOCALIZACAO)) {
            val localizacao = intent.getStringExtra(Node.NODE_LOCALIZACAO)
            node_localizacao.setText(localizacao)
        }
        if (intent.hasExtra(Node.NODE_DESCRICAO)) {
            val descricao = intent.getStringExtra(Node.NODE_DESCRICAO)
            node_localizacao.setText(descricao)
        }

        //TODO (4) Adição de outras funcionalidades

        button_go_to_maps.setOnClickListener(View.OnClickListener { goToMaps() })

        button_go_to_web.setOnClickListener(View.OnClickListener { goToWeb() })

        button_go_to_camera.setOnClickListener(View.OnClickListener { goToCamera() })
    }

    /**
     * TODO (5) Implemtentação do método para navegar para o maps.
     * Fonte: https://developers.google.com/maps/documentation/android-api/intents?hl=pt-br
     */
    fun goToMaps() {
        //Cria URI
        val addressString = node_localizacao.getText().toString()
        val addressUri = Uri.parse("geo:0,0?q=$addressString")

        //Navega de fato para o maps
        val intent = Intent(Intent.ACTION_VIEW, addressUri)
        intent.setPackage("com.google.android.apps.maps")
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    fun goToWeb() {
        //Cria URI
        val addressString = "https://www.remot.co/"
        val uri = Uri.parse(addressString)

        //Direciona  para o navegador
        val intent = Intent(Intent.ACTION_VIEW, uri)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }


    internal val REQUEST_IMAGE_CAPTURE = 1

    /**
     * Redireciona para camera
     * Para recuperar a imagem, verificar documentação.
     * Fonte: https://developer.android.com/training/camera/photobasics.html#TaskCaptureIntent
     */
    fun goToCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }
}
