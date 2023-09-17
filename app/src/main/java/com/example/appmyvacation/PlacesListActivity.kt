package com.example.appmyvacation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appmyvacation.databinding.ActivityMainBinding

class PlacesListActivity : AppCompatActivity() {

    // Referencia para el binding
    private lateinit var binding: ActivityMainBinding
    val places = listOf<Place>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar el binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura el RecyclerView.
        val adapter = PlacesAdapter(places)
        binding.recyclerView.adapter = adapter    // Cambia rvPlacesList por recyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)  // Cambia rvPlacesList por recyclerView

        binding.btnSavePlace.setOnClickListener() {
            // Abrir actividad para agregar un lugar
        }
    }
}


