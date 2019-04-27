package br.com.ceiot.aula02

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

   /*TODO: (1) Criação das váriaveis.
   Criar:
   Ums constante que contém a quantidade de nós que será criada
   Uma lista que irá conter a lista de nós
   Um ListView que será a referência da tela aqui na Activity
   */
    private val NUMBER_OF_NODES = 30
    private var nodeList: ArrayList<String> = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO: (2) Inicialização da lista de nós
        for (n in 0..NUMBER_OF_NODES) {
            nodeList.add("Node $n")
        }

        /*
            TODO: (3) ArrayAdapter
            Como vincular os dados de nodeList ao ListView?

            Deve-se criar um Adapter!

            A forma mais simples é usar o ArrayAdapter. Este adapter converte
            uma ArrayList de objetos em itens View carregados no contêiner ListView.

            O Adapter requer um layout, nesse exemplo utilizamos simple_list_item_1
            (Exemplo padrão Android)

            Fonte: https://developer.android.com/reference/android/widget/ArrayAdapter.html
            https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
         */
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, nodeList)

        //TODO: (4) Inicialização do listView
        list_view.adapter = adapter
    }
}
