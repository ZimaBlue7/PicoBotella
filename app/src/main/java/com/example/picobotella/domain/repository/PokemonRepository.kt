package com.example.picobotella.domain.repository

import com.example.picobotella.data.remote.model.Pokemon

interface PokemonRepository {

    suspend fun getRandomPokemon(): Pokemon
}