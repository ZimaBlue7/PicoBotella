package com.example.picobotella.data.remote

import com.example.picobotella.data.remote.model.PokedexResponse
import retrofit2.http.GET

interface PokemonApi {

    @GET("master/pokedex.json")
    suspend fun getPokedex(): PokedexResponse
}