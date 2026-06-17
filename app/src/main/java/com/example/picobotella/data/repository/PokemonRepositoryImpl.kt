package com.example.picobotella.data.repository

import com.example.picobotella.data.remote.model.Pokemon
import com.example.picobotella.data.remote.PokemonApi
import com.example.picobotella.domain.repository.PokemonRepository

class PokemonRepositoryImpl(
    private val pokemonApi: PokemonApi
) : PokemonRepository {

    override suspend fun getRandomPokemon(): Pokemon {

        return pokemonApi
            .getPokedex()
            .pokemon
            .random()
    }
}