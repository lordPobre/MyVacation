package com.example.appmyvacation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class PlacesAdapter(private var places: List<Place>) : RecyclerView.Adapter<PlacesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Infla el diseño de un elemento individual (item_place.xml) y retorna un nuevo ViewHolder
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_place, parent, false)
        return ViewHolder(view)
    }
    // Este método vincula los datos del objeto Place con el ViewHolder para ser mostrados en la lista.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = places[position]
        holder.bind(place)
    }
    // Retorna la cantidad total de elementos en la lista
    override fun getItemCount(): Int {
        return places.size
    }
    // Método para actualizar la lista de lugares en el adaptador y refrescar la vista.
    fun updateData(newPlaces: List<Place>) {
        places = newPlaces
        notifyDataSetChanged()
    }
    // ViewHolder define la vista para un elemento individual en el RecyclerView
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val placeNameTextView: TextView = itemView.findViewById(R.id.placeNameTextView)
        private val placeImageView: CircleImageView = itemView.findViewById(R.id.placeImageView)
        // Función que vincula los datos de un objeto Place con los elementos de la vista.
        fun bind(place: Place) {
            placeNameTextView.text = place.name
            // Cargar la imagen utilizando Glide o la biblioteca de tu elección
            Glide.with(itemView)
                .load(place.imageUrl)
                .placeholder(R.drawable.default_image) // Imagen predeterminada en caso de error o carga
                .into(placeImageView)
        }
    }
}

