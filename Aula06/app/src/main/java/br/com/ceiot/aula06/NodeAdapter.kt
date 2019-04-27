package br.com.ceiot.aula06

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_node.view.*

/**
 * RecyclerView.Adapter
 * Criação da classe Adapter, que deve extender RecyclerView.Adapter<NodeAdapter.ViewHolder>
 */
class NodeAdapter (private val nodeList:MutableList<Node>, private val listener:ListItemClickListener) : RecyclerView.Adapter<NodeViewHolder>() {

    /**
     * Este método é chamado quando cada ViewHolder é criada. Ou seja, quando o RecyclerView também
     * for criado. Será criada a quantidade de ViewHolders suficiente para preencher a tela.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained within.
     * @param viewType  If your RecyclerView has more than one type of item (which ours doesn't) you
     *                  can use this viewType integer to provide a different layout. See
     *                  {@link android.support.v7.widget.RecyclerView.Adapter#getItemViewType(int)}
     *                  for more details.
     * @return A new NumberViewHolder that holds the View for each list item
     */
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): NodeViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val view = inflater.inflate(R.layout.item_node, viewGroup, false)
        return NodeViewHolder(view,listener)
    }

    /**
     * Retorna o tamanho da lista a ser apresentada.
     * @return
     */
    override fun getItemCount(): Int {
        return nodeList.size
    }

    /**
     * Este método é chamado pelo RecyclerView para exibir os dados na posição especificada.
     * O conteúdo do ViewHolder é atualizado para exibir os índices corretos na lista
     * de acordo com o parâmetro position.
     * @param holder O View Holder
     * @param position
     */
    override fun onBindViewHolder(holder: NodeViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.bind(nodeList.get(position))
    }

    /*
        Métodos auxiliares.
     */

    /**
     * Método para reiniciar a lista
     */
    fun resetList() {
        val size = nodeList.size
        nodeList.clear()
        notifyItemRangeRemoved(0, size)
    }

    /**
     * Método para adicionar um novo item.
     * @param node
     */
    fun adicionarItem(node: Node) {
        nodeList.add(node)
        notifyItemInserted(itemCount)
    }

    /**
     * Método para remover um item de uma determinada posição.
     * @param position
     */
    private fun removerItem(position: Int) {
        nodeList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, nodeList.size)
    }

    /**
     * Método que recupera item de uma determinada posição
     * @param position
     * @return
     */
    public fun getItem(position: Int): Node {
        return nodeList.get(position)
    }

    /**
     * Método que atualiza item a partir de uma determinada posição.
     * @param position
     * @param node
     */
    private fun atualizarItem(position: Int, node: Node) {
        nodeList.set(position, node)
        notifyItemChanged(position)
    }

}