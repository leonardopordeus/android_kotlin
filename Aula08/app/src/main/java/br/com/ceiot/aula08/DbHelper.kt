package br.com.ceiot.aula08

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * TODO (7) Adicionar classe para interface com o SQLite.
 */
class DbHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION) {

    /**
     * Called when the tasks database is created for the first time.
     */
    override fun onCreate(db: SQLiteDatabase) {

        // Create tasks table (careful to follow SQL formatting rules)
        val CREATE_TABLE = "CREATE TABLE " + NodeContract.Node.TABLE_NAME + " (" +
                NodeContract.Node._ID + " INTEGER PRIMARY KEY, " +
                NodeContract.Node.COLUMN_NAME + " TEXT NOT NULL, " +
                NodeContract.Node.COLUMN_DESCRIPTION + " TEXT NOT NULL);"

        db.execSQL(CREATE_TABLE)
    }

    /**
     * This method discards the old table of data and calls onCreate to recreate a new one.
     * This only occurs when the version number for this database (DATABASE_VERSION) is incremented.
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + NodeContract.Node.TABLE_NAME)
        onCreate(db)
    }

    companion object {
        // The name of the database
        private val DATABASE_NAME = "appceiot.db"

        // If you change the database schema, you must increment the database version
        private val VERSION = 1
    }
}
