package com.example.appmyvacation


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import android.widget.Button
import android.widget.EditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch



class AddPlaceActivity : AppCompatActivity() {
    // Referencia al DAO para interactuar con la base de datos de lugares.
    private lateinit var placeDao: PlaceDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Establece el diseño de la actividad.
        setContentView(R.layout.activity_add_place)
        // Obtiene referencias a los componentes de la UI usando findViewById.
        val btnSavePlace: Button = findViewById(R.id.btnSavePlace)
        val etPlaceName: EditText = findViewById(R.id.etPlaceName)
        val etVisitOrder: EditText = findViewById(R.id.etVisitOrder)
        val etImageUrl: EditText = findViewById(R.id.etImageUrl)
        val etLatitude: EditText = findViewById(R.id.etLatitude)
        val etLongitude: EditText = findViewById(R.id.etLongitude)
        val etComments: EditText = findViewById(R.id.etComments)


        // Inicializa el DAO para interactuar con la base de datos.
        placeDao = AppDatabase.getInstance(applicationContext).placeDao()

        // Establece un listener en el botón para guardar el nuevo lugar.
        btnSavePlace.setOnClickListener {
            // Extrae la información introducida por el usuario.
            val placeName = etPlaceName.text.toString()
            val visitOrder = etVisitOrder.text.toString().toInt()
            val imageUrl = etImageUrl.text.toString()
            val latitude = etLatitude.text.toString().toDouble()
            val longitude = etLongitude.text.toString().toDouble()
            val comments = etComments.text.toString()
            // Crea un objeto 'Place' con la información introducida.
            val place = Place(
                name = placeName,
                visitOrder = visitOrder,
                imageUrl = imageUrl,
                latitude = latitude,
                longitude = longitude,
                comments = comments,
                photoUri = "someUriStringHere"
            )
            // Inserta el nuevo lugar en la base de datos en un thread secundario.
            lifecycleScope.launch(Dispatchers.IO) {
                placeDao.insert(place)
            }
            // Finaliza la actividad y regresa a la anterior.
            finish()
        }
    }
}


