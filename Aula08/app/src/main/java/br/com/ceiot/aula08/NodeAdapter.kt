package br.com.ceiot.aula08

import android.content.Context
import android.database.Cursor
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_node.view.*

/**
 * TODO(4) Alterar NodeAdapter
 * RecyclerView.Adapter
 * Criação da classe Adapter, que deve extender RecyclerView.Adapter<NodeAdapter.ViewHolder>
 */
class NodeAdapter (private val context: Context) : RecyclerView.Adapter<NodeViewHolder>() {

    private var mCursor: Cursor? = null

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
        return NodeViewHolder(view)
    }

    /**
     * Retorna o tamanho da lista a ser apresentada.
     * @return
     */
    override fun getItemCount(): Int {
        return if (mCursor == null) {
            0
        } else mCursor?.count!!
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

        var idIndex = mCursor?.getColumnIndex(NodeContract.Node._ID)
        var nameIndex = mCursor?.getColumnIndex(NodeContract.Node.COLUMN_NAME)
        var descriptionIndex = mCursor?.getColumnIndex(NodeContract.Node.COLUMN_DESCRIPTION)

        mCursor?.moveToPosition(position)

        // Determine the values of the wanted data
        val id = mCursor?.getInt(idIndex!!)
        val name = mCursor?.getString(nameIndex!!)
        val description = mCursor?.getString(descriptionIndex!!)

        //Set values
        holder.itemView.tag = id
        holder.itemView.item_node_name.setText(name)
        holder.itemView.item_node_desc.setText(description)
    }

    /**
     * TODO (6) Criar o método swapCursor
     * When data changes and a re-query occurs, this function swaps the old Cursor
     * with a newly updated Cursor (Cursor c) that is passed in.
     * @param c
     * @return
     */
    fun swapCursor(c: Cursor?): Cursor? {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor === c) {
            return null // bc nothing has changed
        }
        val temp = mCursor
        this.mCursor = c // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged()
        }
        return temp
    }
}