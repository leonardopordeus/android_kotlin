package br.com.ceiot.aula06

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_node_info.*
import kotlinx.android.synthetic.main.fragment_node_info.view.*


/*
 * TODO (0) Para criar um novo fragment
 *  1) Clicar com o botão direito nas pastas que contém código
 *  2) New => Fragment => EmptyActivity
 *  3) Fragment Name: NodeInfoFragment
 *     Fragment Layout name: fragment_node_info
 *
 *     Permitem a adição, a remoção e a substituição de fragmentos em uma atividade em tempo de
 *     execução para criar uma UI dinâmica.
 *
 *     Um Fragment representa o comportamento ou uma parte da interface do usuário em um Activity.
 *     É possível combinar vários fragmentos em uma única atividade para compilar uma IU de vários
 *     painéis e reutilizar um fragmento em diversas atividades. Um fragmento é como uma seção
 *     modular de uma atividade, que tem o próprio ciclo de vida, recebe os próprios eventos de
 *     entrada e que pode ser adicionado ou removido com a atividade em execução (uma espécie de
 *     "subatividade" que pode ser reutilizada em diferentes atividades).
 *
 *     Um fragmento deve sempre ser embutido em uma atividade e o ciclo de vida dele é diretamente
 *     impactado pelo ciclo de vida da atividade do host. Por exemplo, quando a atividade é
 *     pausada, todos os fragmentos também são e, quando a atividade é destruída, todos os fragmentos
 *     também são. No entanto, enquanto uma atividade estiver em execução (estiver no estado do ciclo
 *     de vida retomado), é possível processar cada fragmento independentemente, como adicionar ou
 *     removê-los.
 *
 *     Fonte:https://developer.android.com/guide/components/fragments.html?hl=pt-br
 *     https://developer.android.com/training/basics/fragments/fragment-ui.html?hl=pt-br
 */
/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [NodeInfoFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [NodeInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class NodeInfoFragment : Fragment() {
    // TODO (5) Parametros
    private var name: String? = null
    private var description: String? = null
    private var location: String? = null


    companion object {
        /**
         * TODO (6) Factory utilizada paa criar uma nova instância deste fragment e inicializar os parâmetros.
         * @param name
         * @param descripton
         * @param location
         * @return
         */
        @JvmStatic
        fun newInstance(name: String?, descripton: String?, location:String?) =
            NodeInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(Node.NODE_NOME, name)
                    putString(Node.NODE_DESCRICAO, descripton)
                    putString(Node.NODE_LOCALIZACAO, location)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            // TODO (7) Recupera parametros recebidos
            name = it.getString(Node.NODE_NOME)
            description = it.getString(Node.NODE_DESCRICAO)
            location = it.getString(Node.NODE_LOCALIZACAO)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //TODO (8) Inicialização valores dos TextViews
        val view = inflater.inflate(R.layout.fragment_node_info, container, false)
        view.node_name?.setText(name)
        view.node_description?.setText(description)
        view.node_location?.setText(location)
        view.button_go_to_maps?.setOnClickListener({ goToMaps() })
        view.button_go_to_web?.setOnClickListener( { goToWeb() })
        view.button_go_to_camera?.setOnClickListener( { goToCamera() })

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    /**
     * Implemententação do método para navegar para o maps.
     * Fonte: https://developers.google.com/maps/documentation/android-api/intents?hl=pt-br
     */
    private fun goToMaps() {
        //Cria URI
        val addressString = node_location.getText().toString()
        val addressUri = Uri.parse("geo:0,0?q=$addressString")

        //Navega de fato para o maps
        val intent = Intent(Intent.ACTION_VIEW, addressUri)
        intent.setPackage("com.google.android.apps.maps")
        if (intent.resolveActivity(activity!!.packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun goToWeb() {
        //Cria URI
        val addressString = "https://www.remot.co/"
        val uri = Uri.parse(addressString)

        //Direciona  para o navegador
        val intent = Intent(Intent.ACTION_VIEW, uri)
        if (intent.resolveActivity(activity!!.packageManager) != null) {
            startActivity(intent)
        }
    }


    internal val REQUEST_IMAGE_CAPTURE = 1

    /**
     * Redireciona para camera
     * Para recuperar a imagem, verificar documentação.
     * Fonte: https://developer.android.com/training/camera/photobasics.html#TaskCaptureIntent
     */
    private fun goToCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(activity!!.packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }
}
