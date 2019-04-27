package br.com.ceiot.aula08

import android.content.*
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri

/**
 * TODO (8) Criar o contentProvider
 */
class AppCeiotContentProvider : ContentProvider() {

    private var mDbHelper: DbHelper? = null

    override fun onCreate(): Boolean {
        //Inicializa ContentProvider
        val context = context
        mDbHelper = DbHelper(context!!)
        return true
    }

    /**
     * Implementação do método que realiza Consultas no SQLite.
     * @param uri
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return
     */
    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        val db = mDbHelper!!.readableDatabase
        //Recupera qual funcionalidade de acordo com a URL;
        val match = sUriMatcher.match(uri)
        val retCursor: Cursor
        // Query for the tasks directory and write a default case
        when (match) {
            // Query for the tasks directory
            NODES -> retCursor = db.query(
                NodeContract.Node.TABLE_NAME,
                projection,
                selection,
                selectionArgs, null, null,
                sortOrder
            )
            else -> throw UnsupportedOperationException("Unknown uri: $uri")
        }
        retCursor.setNotificationUri(context!!.contentResolver, uri)
        return retCursor
    }

    /**
     * Implementação do método que realiza o Inserts no SQLite.
     * @param uri
     * @param values
     * @return
     */
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val db = mDbHelper!!.writableDatabase
        val match = sUriMatcher.match(uri)
        val returnUri: Uri

        when (match) {
            NODES -> {
                val id = db.insert(NodeContract.Node.TABLE_NAME, null, values)
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(NodeContract.Node.CONTENT_URI, id)
                } else {
                    throw android.database.SQLException("Failed to insert row into $uri")
                }
            }

            else -> throw UnsupportedOperationException("Unknown uri: $uri")
        }

        context!!.contentResolver.notifyChange(uri, null)

        return returnUri
    }

    /**
     * Implementação do método que realiza o deletes no SQLite.
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return
     */
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val db = mDbHelper!!.writableDatabase
        val match = sUriMatcher.match(uri)
        val tasksDeleted: Int
        when (match) {
            // Handle the single item case, recognized by the ID included in the URI path
            NODE_WITH_ID -> {
                val id = uri.pathSegments[1]
                tasksDeleted = db.delete(NodeContract.Node.TABLE_NAME, "_id=?", arrayOf(id))
            }
            else -> throw UnsupportedOperationException("Unknown uri: $uri")
        }
        if (tasksDeleted != 0) {

            context!!.contentResolver.notifyChange(uri, null)
        }
        return tasksDeleted
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun getType(uri: Uri): String? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    companion object {

        val NODES = 0
        val NODE_WITH_ID = 1

        private val sUriMatcher = buildUriMatcher()

        fun buildUriMatcher(): UriMatcher {

            val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
            uriMatcher.addURI(NodeContract.AUTHORITY, NodeContract.PATH_NODES, NODES)
            uriMatcher.addURI(NodeContract.AUTHORITY, NodeContract.PATH_NODES + "/#", NODE_WITH_ID)

            return uriMatcher
        }
    }
}
