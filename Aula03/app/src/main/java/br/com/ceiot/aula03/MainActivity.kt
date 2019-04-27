package br.com.ceiot.aula03

import android.content.Context
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ref.WeakReference
import java.util.ArrayList

/**
 * TODO: (0) Criar um menu.
 * Para criar um menu:
 *  1) Clicar com o botão direito na pasta res
 *  2) New => Android Resource Directory
 *  3) Directory Name: menu
 *     Resource type: menu
 *  4) Clicar com o botão direito na pasta res/menu
 *  5) New => Menu Resource File
 *  6) File name: main
 *     Source Set: main
 *     Directory name: menu
 *
 *  Fonte: https://developer.android.com/guide/topics/ui/menus.html?hl=pt-br
 */
class MainActivity : AppCompatActivity() {

    var nodeList: ArrayList<String> = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, nodeList)
        list_view.adapter = adapter
    }

    /*
        TODO (2) Criar o menu na activity
        Para isso deve-se sobrescrever o método onCreateOptionsMenu
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater?.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /*
        TODO (3) Criar o click do menu na activity
        Para isso deve-se sobrescrever o método onOptionsItemSelected e fazer a tratativa em código.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemThatWasClickedId = item.itemId
        if (itemThatWasClickedId == R.id.action_search) {
            pesquisar()
            return true
        } else if (itemThatWasClickedId == R.id.action_create) {
            //TODO (11): Nova ação para criar um Node
            criar()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    /*
        TODO: (4) Implementar a pesquisa.
     */
    fun pesquisar() {
        /*
            https://developer.android.com/guide/topics/ui/notifiers/toasts.html
         */
        Toast.makeText(this, "Pesquisa OK", Toast.LENGTH_SHORT).show()

        //TODO (5): Executa o serviço em paralelo
        NodeSearchTask(this).execute("")
    }

    /**
     * TODO (12): Criação de rotina para salvar um Node
     */
    private fun criar() {
        Toast.makeText(this, "Criar", Toast.LENGTH_SHORT).show()
        NodeCreateTask(this).execute("")
    }

}
