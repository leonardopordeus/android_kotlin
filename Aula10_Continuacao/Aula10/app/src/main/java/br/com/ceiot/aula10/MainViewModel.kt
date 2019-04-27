package br.com.ceiot.aula10

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import android.util.Log

import br.com.ceiot.aula10.br.com.ceiot.aula10.entity.AppDatabase
import br.com.ceiot.aula10.br.com.ceiot.aula10.entity.Node

/**
 * TODO(6) Armazena dados da UI que não são destruidos em rotações
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {

    var nodes: LiveData<List<Node>>? = null
    init {
        val database = AppDatabase.getInstance(this.getApplication())
        Log.d(TAG, "Actively retrieving the tasks from the DataBase")
        if (database != null) {
            nodes = database.nodeDao().loadAllNodes()
        }
    }

    companion object {

        // Constant for logging
        private val TAG = MainViewModel::class.java.simpleName
    }
}
