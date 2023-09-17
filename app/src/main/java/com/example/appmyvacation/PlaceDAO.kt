package com.example.appmyvacation

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface PlaceDao {
    // Anotaciónes para indicar que estos métodos son para insertar, actualizar y eliminar una entidad "Place" en la base de datos.
    @Insert
    suspend fun insert(place: Place)

    @Update
    suspend fun update(place: Place)

    @Delete
    suspend fun delete(place: Place)
    // Anotación con una consulta SQL para seleccionar y retornar todas las entradas de la tabla "place".
    @Query("SELECT * FROM place")
    suspend fun getAllPlaces(): List<Place>


}


