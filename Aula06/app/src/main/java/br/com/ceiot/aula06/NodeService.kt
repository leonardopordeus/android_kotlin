package br.com.ceiot.aula06

import retrofit2.Call
import retrofit2.http.*

interface NodeService
{
    @GET("/{entity}.json")
    fun list(@Path("entity") entity:String) : Call<Map<String,Node>>

    @POST("/{entity}.json")
    fun saveOnFirebase(@Path("entity") entity:String,@Body node:Node) : Call<Map<String,String>>
}