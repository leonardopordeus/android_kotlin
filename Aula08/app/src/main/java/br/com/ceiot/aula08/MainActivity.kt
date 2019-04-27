package br.com.ceiot.aula08

import android.content.Intent
import android.database.Cursor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.AsyncTaskLoader
import android.support.v4.content.Loader
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    private val TAG = MainActivity::class.java.simpleName
    private val NODE_LOADER_ID = 0

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
        var mAdapter: RecyclerView.Adapter<*>? = NodeAdapter(this)
        my_recycler_view.adapter = mAdapter

        //TODO (12) Implementar acao pra adicionar Node
        fab.setOnClickListener(
            {
            // Create a new intent to start an AddTaskActivity
            val addTaskIntent = Intent(this@MainActivity, AddNodeActivity::class.java)
            startActivity(addTaskIntent)
        })

        /*
            TODO (14) Adicionar "deslizar para deletar"
         */
        val swipeCallback = object : SwipeCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val id = viewHolder.itemView.tag as Int
                val stringId = Integer.toString(id)
                var uri = NodeContract.Node.CONTENT_URI
                uri = uri.buildUpon().appendPath(stringId).build()
                contentResolver.delete(uri, null, null)
                supportLoaderManager.restartLoader(NODE_LOADER_ID, null, this@MainActivity)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeCallback)
        itemTouchHelper.attachToRecyclerView(my_recycler_view)

        //Inicia Loader
        LoaderManager.getInstance(this).initLoader(NODE_LOADER_ID, null, this)
    }

    /**
     * TODO (15) Implementacao para recuperar informações ao voltar a atividade principal.
     */
    override fun onResume() {
        super.onResume()
        LoaderManager.getInstance(this).restartLoader(NODE_LOADER_ID, null, this)
    }


    /**
     * TODO (9) Implementar LoaderManager
     * Inicializa o Loader e realiza uma consulta local.
     * @param id
     * @param args
     * @return
     */
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return object : AsyncTaskLoader<Cursor>(this) {
            //Inicializa cursor
            internal var mData: Cursor? = null

            override fun onStartLoading() {
                if (mData !=
                    null
                ) {
                    deliverResult(mData)
                } else {
                    forceLoad()
                }
            }

            override fun loadInBackground(): Cursor? {
                try {
                    return contentResolver.query(
                        NodeContract.Node.CONTENT_URI, null, null, null,
                        NodeContract.Node._ID
                    )

                } catch (e: Exception) {
                    Log.e(TAG, "Failed to asynchronously load data.")
                    e.printStackTrace()
                    return null
                }

            }

            // deliverResult sends the result of the load, a Cursor, to the registered listener
            override fun deliverResult(data: Cursor?) {
                mData = data
                super.deliverResult(data)
            }
        }
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        var adapter: NodeAdapter = my_recycler_view.adapter as NodeAdapter
        adapter.swapCursor(data)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        var adapter: NodeAdapter = my_recycler_view.adapter as NodeAdapter
        adapter.swapCursor(null)
    }

}
