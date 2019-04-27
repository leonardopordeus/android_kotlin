package br.com.ceiot.aula01

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

/**
 * TODO: (0) Activity
 * Activity é um componente de aplicativo que fornece uma tela com a qual os usuários podem
 * interagir para fazer algo, como discar um número no telefone, tirar uma foto, enviar um e-mail
 * ou ver um mapa. Cada atividade recebe uma janela que exibe a interface do usuário.
 * Geralmente, a janela preenche a tela, mas pode ser menor que a tela e flutuar sobre outras
 * janelas.
 *
 * Aplicativos geralmente têm várias atividades pouco vinculadas entre si. Normalmente, uma
 * atividade em um aplicativo é especificada como "principal", que é a apresentada ao usuário ao
 * iniciar o aplicativo pela primeira vez. Cada atividade pode, então, iniciar outra atividade para
 * executar diferentes ações. Ao iniciar uma nova atividade, a atividade anterior é interrompida,
 * mas o sistema conserva a atividade em uma pilha (a "pilha de retorno").
 *
 * Fonte: https://developer.android.com/guide/components/activities.html?hl=pt-br
 *
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
        TODO: (7) Referência e Classe R
        Quando sua aplicação é compilada, a classe R é gerada. Isso cria constantes que permitem
        identificar os conteúdos da pasta res, incluindo layouts
        */
        setContentView(R.layout.activity_main)

        /*
        TODO: (8) Action
        O código abaixo cria um listener que irá disparar uma ação quando o botão tiver um click.
        */
        button_example.setOnClickListener {
            textView_aula01_0.text = "AULAAAAAAAAAA"
            textView_aula01_1.text = "CEIOT"
        }
    }
}
