package br.com.ceiot.aula04

/**
 * TODO (6) Criação da interface ListItemClickListener;
 * Interface que receberá o evento onClick.
 * Definição de um handler para interfacear o RecyclerView com a Activity
 * An on-click handler that we've defined to make it easy for an Activity to interface with
 * our RecyclerView
 */
interface ListItemClickListener {
    fun onListItemClick(clickedItemIndex: Int)
}