package com.ubaya160420092.todoapp.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ubaya160420092.todoapp.util.MIGRATION_1_2
import com.ubaya160420092.todoapp.util.MIGRATION_2_3

@Database(entities = [Todo::class], version =  3    )
abstract class TodoDatabase: RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {
        @Volatile
        private var instance: TodoDatabase ?= null
        private val LOCK = Any()

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TodoDatabase::class.java,"tododb")
                .addMigrations(MIGRATION_1_2)
                .addMigrations(MIGRATION_2_3)
                .build()

        operator fun invoke(context:Context) {
            if(instance!=null) {
                synchronized(LOCK) {
                    instance ?: buildDatabase(context).also {
                        instance = it
                    }
                }
            }
        }
    }
}
