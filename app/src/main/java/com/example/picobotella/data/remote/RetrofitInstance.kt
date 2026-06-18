package com.example.picobotella.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Objeto singleton encargado de crear y proporcionar una única instancia
 * de Retrofit para toda la aplicación.
 *
 * Retrofit es una librería que permite consumir servicios web (APIs REST)
 * de forma sencilla, convirtiendo automáticamente las respuestas JSON
 * en objetos de Kotlin.
 *
 * En este proyecto se utiliza para conectarse a la Pokédex pública alojada
 * en GitHub y obtener la información de los Pokémon que se muestran
 * en los retos del juego.
 */
object RetrofitInstance {

    /**
     * URL base del servicio web.
     *
     * Todas las peticiones definidas en PokemonApi se construirán
     * a partir de esta dirección.
     */
    private const val BASE_URL =
        "https://raw.githubusercontent.com/Biuni/PokemonGO-Pokedex/"

    /**
     * Instancia única de la interfaz PokemonApi.
     *
     * - Retrofit.Builder() crea la configuración de Retrofit.
     * - baseUrl() define la dirección principal del servicio.
     * - GsonConverterFactory permite convertir JSON a objetos Kotlin.
     * - create() genera la implementación de la interfaz PokemonApi.
     *
     * El uso de 'lazy' garantiza que la instancia solo se cree cuando
     * sea necesaria por primera vez, optimizando recursos.
     */
    val api: PokemonApi by lazy {

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(PokemonApi::class.java)
    }
}