package dev.stocky37.pokeapi.jaxrs

import dev.stocky37.pokeapi.client.PokeApiClient
import dev.stocky37.pokeapi.jaxrs.api.PokeApiService

class PokeApiJaxrsClient internal constructor(private val service: PokeApiService) : PokeApiClient {
	override val pokemon by lazy { PokemonJaxrsClient(service.pokemon()) }
}