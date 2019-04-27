package br.com.ceiot.aula06

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

//Implementar ListItemClickListener
class MainActivity : AppCompatActivity(), ListItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        Inicialização do Recycler View

        Recycler View:
        Componente que ficará na Activity/Fragment e irá apresentar a lista na tela do usuário,
        assim como outros campos (Button, Text View, etc).


        Fonte:
        https://developer.android.com/guide/topics/ui/layout/recyclerview.html
        https://developer.android.com/training/material/lists-cards.html?hl=pt-br
         */
        //mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        my_recycler_view.setHasFixedSize(true)

        /*
        LayoutManager:
        É o gerenciador de layout. Nele é definido qual será a disposição do itens da lista.
        Neste caso, LinearLayout.
        */
        var mLayoutManager: RecyclerView.LayoutManager? = LinearLayoutManager(this)
        my_recycler_view.setLayoutManager(mLayoutManager)

        /*
        Adapter:
        Classe responsável por associar a lista com o conteúdo à view correspondente.
        Cada objeto da lista será um item na view. O Adpter também é utilizado para definir se um item
        será exibido ou não.
        */
        var nodeListRecycler: MutableList<Node> = ArrayList()
        var mAdapter:RecyclerView.Adapter<*>? = NodeAdapter(nodeListRecycler,this)
        my_recycler_view.adapter = mAdapter
    }

    /*
        Criar o menu na activity
        Para isso deve-se sobrescrever o método onCreateOptionsMenu
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /*
        Criar o click do menu na activity
        Para isso deve-se sobrescrever o método onOptionsItemSelected e fazer a tratativa em código.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemThatWasClickedId = item.itemId
        if (itemThatWasClickedId == R.id.action_search) {
            pesquisar()
            return true
        } else if (itemThatWasClickedId == R.id.action_create) {
            //Nova ação para criar um Node
            criar()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    /*
        Implementar a pesquisa.
     */
    fun pesquisar() {
        /*
            https://developer.android.com/guide/topics/ui/notifiers/toasts.html
         */
        Toast.makeText(this, "Pesquisa OK", Toast.LENGTH_SHORT).show()

        //Executa o serviço em paralelo
        NodeSearchTask(this).execute("")
    }

    /**
     * Criação de rotina para salvar um Node
     */
    private fun criar() {
        Toast.makeText(this, "Criar", Toast.LENGTH_SHORT).show()
        NodeCreateTask(this).execute("")
    }

    /**
     * Implementação do método que irá tratar o evento de onclick
     * @param clickedItemIndex
     */
    override fun onListItemClick(clickedItemIndex: Int) {
        val toastMessage = "Item #$clickedItemIndex clicked."
        Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show()

        //TODO (2) Alterar ação do click para abrir uma nova activity
        var adapter: NodeAdapter = my_recycler_view.adapter as NodeAdapter
        val node: Node = adapter.getItem(clickedItemIndex)

        //Armazena a referência de um contexto em uma variável./
        val context = this@MainActivity

        //Seleciona a activity de destino
        val destinationActivity = NodeActivity::class.java

        /*
         * Here, we create the Intent that will start the Activity we specified above in
         * the destinationActivity variable. The constructor for an Intent also requires a
         * context, which we stored in the variable named "context".
         */
        val startChildActivityIntent = Intent(context, destinationActivity)
        startChildActivityIntent.putExtra(Node.NODE_NOME, node.name)
        startChildActivityIntent.putExtra(Node.NODE_DESCRICAO, node.description)
        startChildActivityIntent.putExtra(Node.NODE_LOCALIZACAO, node.location)

        /*
         * Once the Intent has been created, we can use Activity's method, "startActivity"
         * to start the ChildActivity.
         */
        startActivity(startChildActivityIntent)
    }
}
