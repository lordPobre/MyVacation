package com.example.appmyvacation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appmyvacation.databinding.ActivityPlaceDetailBinding

class PlaceDetailActivity : AppCompatActivity() {
    // Declara una propiedad "binding" que representa las vistas del layout asociado.
    private lateinit var binding: ActivityPlaceDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inicializa la propiedad "binding" inflando el layout "ActivityPlaceDetailBinding".
        binding = ActivityPlaceDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Obtiene el objeto "Place" pasado como extra a través del Intent.
        val place = intent.getParcelableExtra<Place>("place")
        // Configura el texto del TextView "tvPlaceName" con el nombre del lugar obtenido.
        binding.tvPlaceName.text = place?.name
        // Configura un escuchador de clics para el botón "btnEditPlace"
        binding.btnEditPlace.setOnClickListener {
            // Abrir actividad para editar el lugar
        }
    }
}

