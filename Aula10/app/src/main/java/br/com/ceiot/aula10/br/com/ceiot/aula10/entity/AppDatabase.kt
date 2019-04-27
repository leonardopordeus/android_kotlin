package br.com.ceiot.aula10.br.com.ceiot.aula10.entity

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.util.Log

/**
 * TODO(3)  representação da classe abstrata do Banco de Dados
 */
@Database(entities = arrayOf(Node::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun nodeDao(): NodeDao

    companion object {

        private val LOG_TAG = AppDatabase::class.java!!.getSimpleName()
        private val LOCK = Object()
        private val DATABASE_NAME = "nodelist"
        private var sInstance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (sInstance == null) {
                synchronized(LOCK) {
                    Log.d(LOG_TAG, "Creating new database instance")
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase::class.java, AppDatabase.DATABASE_NAME)
                        .build()
                }
            }
            Log.d(LOG_TAG, "Getting the database instance")
            return sInstance
        }
    }

}
