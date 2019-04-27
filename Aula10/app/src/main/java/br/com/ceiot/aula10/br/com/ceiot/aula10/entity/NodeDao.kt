package br.com.ceiot.aula10.br.com.ceiot.aula10.entity

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

/*
    TODO (2): Criar o Acesso aos dados (DAO)
 */
@Dao
interface NodeDao {

    @Query("SELECT * FROM node ORDER BY id")
    fun loadAllNodes(): LiveData<List<Node>>

    @Insert
    fun insertNode(nodeEntity: Node)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateNode(nodeEntity: Node)

    @Delete
    fun deleteNode(nodeEntity: Node)

    @Query("SELECT * FROM node WHERE id = :id")
    fun loadNodeById(id: Int): LiveData<Node>
}