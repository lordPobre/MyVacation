package com.example.appmyvacation

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

@Database(entities = [Place::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    // Función abstracta que debe ser implementada por Room.
    abstract fun placeDao(): PlaceDao
    // Objeto compañero para funciones y propiedades estáticas.
    companion object {
        // Variable que mantiene una referencia a la instancia de la base de datos.
        @Volatile
        private var INSTANCE: AppDatabase? = null
        // Función para obtener la instancia de la base de datos. Si no existe, la crea.
        fun getInstance(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            // Bloquea el código para garantizar que solo un thread pueda crear la instancia.
            synchronized(this) {
                // Crea la base de datos si no existe.
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
