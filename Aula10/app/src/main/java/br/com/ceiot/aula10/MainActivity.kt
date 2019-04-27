package br.com.ceiot.aula10

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import br.com.ceiot.aula10.br.com.ceiot.aula10.entity.AppDatabase
import br.com.ceiot.aula10.br.com.ceiot.aula10.entity.Node
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() , ListItemClickListener{


    private val TAG = MainActivity::class.java.simpleName


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


        my_recycler_view.setHasFixedSize(true)
        var mLayoutManager: RecyclerView.LayoutManager? = LinearLayoutManager(this)
        my_recycler_view.setLayoutManager(mLayoutManager)

        var mAdapter: RecyclerView.Adapter<*>? = NodeAdapter(this)
        my_recycler_view.adapter = mAdapter

        fab.setOnClickListener(
            {
                // Create a new intent to start an AddTaskActivity
                val addTaskIntent = Intent(this@MainActivity, AddNodeActivity::class.java)
                startActivity(addTaskIntent)
            })

        /**
         * TODO (7) Adicionar "deslizar para deletar"
         */
        val database = AppDatabase.getInstance(this.getApplication())
        val swipeCallback = object : SwipeCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                AppExecutors.instance!!.diskIO().execute(Runnable {
                    val position = viewHolder.adapterPosition
                    val nodes = (mAdapter as NodeAdapter).getNodes()
                    database!!.nodeDao().deleteNode(nodes!!.get(position))
                })
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeCallback)
        itemTouchHelper.attachToRecyclerView(my_recycler_view)

        setupViewModel();
    }

    /**
     * TODO (5) Lista dados e atualiza quando existir mudanças.
     */
    private fun setupViewModel() {
        val viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java!!)
        if(viewModel.nodes != null){
            viewModel.nodes!!.observe(this, object : Observer<List<Node>> {
                override fun onChanged(nodesList: List<Node>?) {
                    Log.d(TAG, "Updating list of nodes from LiveData in ViewModel")
                    var adapter: NodeAdapter = my_recycler_view.adapter as NodeAdapter
                    adapter.setNodes(nodesList)
                }
        })}
    }
    override fun onListItemClick(clickedItemIndex: Int) {
    }
}
