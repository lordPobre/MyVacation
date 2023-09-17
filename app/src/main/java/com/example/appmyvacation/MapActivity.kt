package com.example.appmyvacation

import android.os.Bundle
import org.osmdroid.util.GeoPoint
import androidx.appcompat.app.AppCompatActivity
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView
import org.osmdroid.config.Configuration
import org.osmdroid.views.overlay.Marker




class MapActivity : AppCompatActivity() {

    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        // Configuración inicial de osmdroid
        Configuration.getInstance().load(applicationContext, getSharedPreferences("osmdroid", MODE_PRIVATE))

        mapView = findViewById(R.id.mapview)
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.zoomController.setVisibility(org.osmdroid.views.CustomZoomButtonsController.Visibility.ALWAYS)
        mapView.setMultiTouchControls(true)

        // Para centrar el mapa en una ubicación particular (por ejemplo, latitud 20, longitud 70)
        val mapController = mapView.controller
        mapController.setZoom(9.0)
        val startPoint = GeoPoint(20.0, 70.0)
        mapController.setCenter(startPoint)

        // Añadir un marcador en esa ubicación
        val startMarker = Marker(mapView)
        startMarker.position = startPoint
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        mapView.overlays.add(startMarker)
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume() // necesario para que osmdroid funcione correctamente
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause() // necesario para que osmdroid funcione correctamente
    }
}
