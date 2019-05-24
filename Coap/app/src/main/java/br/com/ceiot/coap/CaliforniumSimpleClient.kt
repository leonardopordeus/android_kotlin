package br.com.ceiot.coap


import org.eclipse.californium.core.*

/**
 * TODO (6) Criada classe para manter as funções do COAP em um único lugar
 */
class CaliforniumSimpleClient {

    /**
     * Implementação da função get
     * @param path
     * @return
     */
    operator fun get(path: String): String? {
        try {
            val client = CoapClient(BASE_URL + path)
            val response = client.get()
            if (response != null) {
                println(response.code)
                println(response.options)
                println(response.payload)
                return response.responseText
            } else {
                return "No response received."
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * Implementação do observe
     * @param path
     * @param handler
     * @return
     */
    fun observe(path: String, handler: CoapHandler): String? {
        try {
            val client = CoapClient(BASE_URL + path)
            val relation = client.observe(handler)
            return if (relation != null && relation.current != null) {
                relation.current.responseText
            } else {
                "No response received."
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * Implementação do discovery
     * @param path
     * @return
     */
    fun discovery(path: String): String? {
        try {
            val client = CoapClient(BASE_URL + path)
            val set = client.discover()

            var itens = ""
            for (wl in set) {
                itens = itens + wl.uri + "\r\n"
            }
            return itens
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    companion object {
        internal val BASE_URL = "coap://localhost:5683/"//"coap://californium.eclipse.org:5683/";
    }
}
