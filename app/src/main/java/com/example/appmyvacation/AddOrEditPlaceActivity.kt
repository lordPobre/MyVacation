package com.example.appmyvacation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appmyvacation.databinding.ActivityAddPlaceBinding

class AddOrEditPlaceActivity : AppCompatActivity() {
    // Referencia para acceder a los elementos de la vista usando ViewBinding.
    private lateinit var binding: ActivityAddPlaceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    // Inicializa el ViewBinding para esta actividad.
        binding = ActivityAddPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)
    // Intenta obtener un objeto 'Place' pasado como extra. Si está presente, significa que estamos editando un lugar existente.
        val placeToEdit = intent.getParcelableExtra<Place>("placeToEdit")
        // Si el lugar a editar no es nulo, rellena los campos de la actividad con sus datos.
        if (placeToEdit != null) {
            // Poblar los campos con los datos del lugar para editar
            binding.etPlaceName.setText(placeToEdit.name)
            binding.etVisitOrder.setText(placeToEdit.visitOrder.toString())
            binding.etImageUrl.setText(placeToEdit.imageUrl)
            binding.etLatitude.setText(placeToEdit.latitude.toString())
            binding.etLongitude.setText(placeToEdit.longitude.toString())
            binding.etAccommodationCost.setText(placeToEdit.accommodationCost.toString())
            binding.etTransportCost.setText(placeToEdit.transportCost?.toString())
            binding.etComments.setText(placeToEdit.comments)
        }

        binding.btnSavePlace.setOnClickListener {
            // Si placeToEdit es null, agregar un nuevo lugar.
            // Si no, actualizar el lugar existente.
        }
    }
}

