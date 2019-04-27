package br.com.ceiot.aula05

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Criando classe para implementação de Serviços Rest.
 */
class RestUtil {

    internal val BASE_URL = "https://teste-1bb2b.firebaseio.com/"
    internal val PARAM_ORDER_BY = "orderBy"
    internal val NOME = "nome"
    internal val EQUAL_TO = "equalTo"

    fun nodeService() : NodeService
    {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(NodeService::class.java)
        return service
    }
}