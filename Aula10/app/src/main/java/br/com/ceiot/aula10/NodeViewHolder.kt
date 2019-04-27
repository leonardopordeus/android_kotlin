package br.com.ceiot.aula10

import android.support.v7.widget.RecyclerView
import android.view.View
import br.com.ceiot.aula10.br.com.ceiot.aula10.entity.Node
import kotlinx.android.synthetic.main.item_node.view.*

/**
 * ViewHolder
 * Apresenta uma referência para a exibição para os itens no RecyclerView.
 * Fonte: https://developer.android.com/reference/android/support/v7/widget/RecyclerView.ViewHolder.html
 * Interface para tratar o evento de click
 */
class NodeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private var mOnClickListener: ListItemClickListener? = null

    constructor(itemView: View, listener:ListItemClickListener) : this(itemView) {
        mOnClickListener = listener
        itemView.setOnClickListener(this)
    }

    /**
     * Implementação do método que é chamado ao quando um item for clicado.
     * @param v The View that was clicked
     */
    override fun onClick(view: View?) {
        val clickedPosition = adapterPosition
        mOnClickListener?.onListItemClick(clickedPosition)
    }

    /**
     * Criação de um método para único para preencher o ViewHolder
     * @param node
     */
    fun bind(node: Node) {
        itemView.item_node_name.setText(node.name)
        itemView.item_node_desc.setText(node.description)
    }
}