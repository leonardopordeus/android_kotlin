package br.com.ceiot.aula10


import android.annotation.SuppressLint
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import br.com.ceiot.aula10.br.com.ceiot.aula10.entity.AppDatabase
import br.com.ceiot.aula10.br.com.ceiot.aula10.entity.Node
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() , ListItemClickListener, LifecycleOwner {


    private val TAG = MainActivity::class.java.simpleName
    private val NODE_LOADER_ID = 0

    private val chargingBroadcastReceiver = ChargingBroadcastReceiver()
    private val filter = IntentFilter()


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
        var mLayoutManager: RecyclerView.LayoutManager? =
            LinearLayoutManager(this)
        my_recycler_view.setLayoutManager(mLayoutManager)

        var mAdapter: androidx.recyclerview.widget.RecyclerView.Adapter<*>? = NodeAdapter(this)
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
            override fun onSwiped(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, direction: Int) {
                AppExecutors.instance!!.diskIO().execute(Runnable {
                    val position = viewHolder.adapterPosition
                    val nodes = (mAdapter as NodeAdapter).getNodes()
                    database!!.nodeDao().deleteNode(nodes!!.get(position))
                })
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeCallback)
        itemTouchHelper.attachToRecyclerView(my_recycler_view)

        setupViewModel()

        scheduleJob()

        //TODO (35) Inicialização do broadcastreceiver
        //https://developer.android.com/guide/components/broadcasts
        filter.addAction(Intent.ACTION_POWER_CONNECTED)
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED)
    }


    override fun onResume() {
        super.onResume()
        registerReceiver(chargingBroadcastReceiver, filter)
    }
    override fun onDestroy() {
        super.onDestroy()
        //unregisterReceiver(chargingBroadcastReceiver)
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

    /**
     * 12) TODO (19) criar agendamento de tarefas.
     */
    fun scheduleJob()
    {
        val constraints = Constraints.Builder()
            //.setRequiresCharging(true)
            .build()

        val work: PeriodicWorkRequest =
            PeriodicWorkRequestBuilder<SearchNodeWorker>(30, TimeUnit.SECONDS)
                // Other setters
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance()
            .enqueueUniquePeriodicWork("searchNode", ExistingPeriodicWorkPolicy.KEEP, work);
        work.id
        WorkManager.getInstance().getWorkInfoByIdLiveData(work.id)
            .observe(this, Observer { workInfo ->
                if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
                    Toast.makeText(this, "Update", Toast.LENGTH_LONG)
                }
            })
    }

}
