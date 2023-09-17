package com.example.appmyvacation


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.appmyvacation.databinding.ActivityMainBinding
import com.google.firebase.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    // Constantes para los códigos de solicitud
    private val CAMERA_REQUEST_CODE = 1001
    private val PHOTO_REQUEST_CODE = 2002
    // Constantes para los códigos de solicitud
    private var currentPhotoPath: String? = null
    private lateinit var database: AppDatabase
    private lateinit var placeDao: PlaceDao
    // Referencias a elementos de la UI
    private lateinit var etPlaceName: EditText
    private lateinit var imageView: ImageView
    private lateinit var etAccommodationCost: EditText
    // Agrega más referencias a otros elementos de la UI si es necesario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar Room
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
        placeDao = database.placeDao()
        // Referencias a elementos de la UI
        etPlaceName = findViewById(R.id.etPlaceName)
        imageView = findViewById(R.id.imageView)
        etAccommodationCost = findViewById(R.id.etAccommodationCost)

        // Verificar y solicitar permiso para usar la cámara
        checkCameraPermission()
        // Configurar el mapa
        val mapView: MapView = findViewById(R.id.mapView)
        mapView.tileProvider.setTileSource(TileSourceFactory.MAPNIK)
        // Establecer el listener para el botón de guardar
        val btnSavePlace = findViewById<Button>(R.id.btnSavePlace)
        btnSavePlace.setOnClickListener { savePlace() }
        // Establecer el listener para el botón de captura de imagen
        val btnCapture = findViewById<Button>(R.id.btnCapture)
        btnCapture.setOnClickListener { dispatchTakePictureIntent() }

        // Configurar RecyclerView para mostrar la lista de lugares
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = PlacesAdapter(emptyList()) // Adaptador personalizado para mostrar lugares
        recyclerView.adapter = adapter
        // Recuperar y actualizar la lista de lugares desde la base de datos
        lifecycleScope.launch {
            val places = withContext(Dispatchers.IO) {
                placeDao.getAllPlaces()
            }
            adapter.updateData(places)
        }
    }
    // Verificar y solicitar permisos de cámara y almacenamiento
    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), CAMERA_REQUEST_CODE)
        }
    }
    // Lanzar la intención para capturar una foto
    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            val photoFile: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                Toast.makeText(this, "Error al crear archivo de imagen", Toast.LENGTH_SHORT).show()
                null
            }

            photoFile?.let {
                val photoURI = FileProvider.getUriForFile(this, "com.tuproyecto.fileprovider", it)
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE)
            }
        }
    }
    // Crear un archivo temporal para la foto
    private fun createImageFile(): File {
        val timeStamp: String =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir).apply {
            currentPhotoPath = absolutePath
        }
    }
    // Manejar el resultado de la solicitud de captura de foto
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            currentPhotoPath?.let { path ->
                imageView.setImageURI(Uri.fromFile(File(path)))
            }
        }
    }
    // Guardar un nuevo lugar en la base de datos
    private fun savePlace() {
        if (currentPhotoPath == null) {
            Toast.makeText(this, "Primero toma una foto", Toast.LENGTH_SHORT).show()
            return
        }

        val placeName = etPlaceName.text.toString()
        val place = Place(
            id = 0,
            name = placeName,
            photoUri = currentPhotoPath!!,
            imageUrl = "",
            visitOrder = 0,
            latitude = 0.0,
            longitude = 0.0,
            accommodationCost = 0.0,
            transportCost = 0.0,
            comments = ""
        )

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                placeDao.insert(place)
            }
        }
    }
    // Manejar la respuesta a la solicitud de permisos
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permiso concedido
                } else {
                    Toast.makeText(this, "Permiso de cámara denegado.", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }
}






