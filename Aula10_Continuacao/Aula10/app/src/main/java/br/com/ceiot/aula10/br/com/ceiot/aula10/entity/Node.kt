package br.com.ceiot.aula10.br.com.ceiot.aula10.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/*
    TODO (1): Criar Entidade Node
 */
@Entity(tableName = "node")
class Node {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var name: String? = null
    var description: String? = null

    @Ignore
    constructor(name: String, description: String) {
        this.name = name
        this.description = description
    }

    constructor(id: Int, name: String, description: String) {
        this.id = id
        this.name = name
        this.description = description
    }

    companion object {
        val COLUMN_ID:String = "id"
        val COLUMN_NAME:String = "name"
        val COLUMN_DESCRIPTION:String = "description"
    }
}