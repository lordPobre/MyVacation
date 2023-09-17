package com.example.appmyvacation


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "place")
@Parcelize
data class Place(
    // Declara "id" como clave primaria y le indica a Room que se debe autogenerar.
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val photoUri: String,
    val visitOrder: Int,
    val imageUrl: String,
    val latitude: Double,
    val longitude: Double,
    val accommodationCost: Double = 0.0,
    val transportCost: Double? = null,
    val comments: String? = null
): Parcelable// Implementa Parcelable para permitir que las instancias de "Place" puedan ser enviadas entre componentes.
