package br.com.ceiot.aula09

import br.com.ceiot.aula09.NodeContract
import retrofit2.Call
import retrofit2.http.*

interface NodeService
{
    @GET("/{entity}.json")
    fun list(@Path("entity") entity:String) : Call<Map<String,NodeContract.Node>>

    @POST("/{entity}.json")
    fun saveOnFirebase(@Path("entity") entity:String,@Body node:NodeContract.Node) : Call<Map<String,String>>
}