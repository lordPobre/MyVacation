package com.example.appmyvacation

import android.app.Activity
import android.content.res.Configuration
import java.util.Locale

open class BaseActivity : Activity() {

    // Esta función permite cambiar el idioma de la aplicación
    fun setAppLanguage(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        resources.updateConfiguration(config, resources.displayMetrics)
    }
    // Llamar a setAppLanguage para cambiar el idioma
    fun cambiarIdioma() {
        setAppLanguage("es") // Cambiar al español
    }
}
