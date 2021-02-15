package dev.stocky37.pokeapi.jaxrs.api

import javax.ws.rs.Path

@Path("v2")
interface PokeApiService {

	@Path("/pokemon")
	fun pokemon(): PokemonService
}