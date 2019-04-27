package br.com.ceiot.aula10

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.ceiot.aula10.br.com.ceiot.aula10.entity.Node

/**
 * RecyclerView.Adapter
 * Criação da classe Adapter, que deve extender RecyclerView.Adapter<NodeAdapter.ViewHolder>
 */
class NodeAdapter (private val listener:ListItemClickListener) : androidx.recyclerview.widget.RecyclerView.Adapter<NodeViewHolder>() {

    private var nodeList:List<Node>? = null
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
        return if (nodeList == null) {
            0
        } else nodeList!!.size
    }

    fun getNodes(): List<Node>? {
        return this.nodeList
    }

    fun setNodes(nodes: List<Node>?) {
        this.nodeList = nodes
        notifyDataSetChanged()
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
        holder.bind(nodeList!!.get(position))
    }

}