package br.com.ceiot.aula08

import android.net.Uri
import android.provider.BaseColumns

/**
 * TODO (3) Criar a classe NodeContract
 * Esta classe será utilizada pelo ContentProvider
 */
object NodeContract {

    // The authority, which is how your code knows which Content Provider to access
    val AUTHORITY = "br.com.ceiot.aula08"

    // The base content URI = "content://" + <authority>
    val BASE_CONTENT_URI = Uri.parse("content://$AUTHORITY")

    // Define the possible paths for accessing data in this contract
    // This is the path for the "tasks" directory
    val PATH_NODES = "nodes"

    /**
     * Criar a classe Node
     * Esta classe será utilizada para recuperar dados do SQLite
     */
    class Node  : BaseColumns {
        companion object  {
            val CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_NODES).build()
            val TABLE_NAME = "node"
            val _ID = "_id"
            val COLUMN_NAME = "name"
            val COLUMN_DESCRIPTION = "description"
            val _COUNT = "_count"
        }
    }
}
