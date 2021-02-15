package dev.stocky37.pokeapi.jaxrs

import dev.stocky37.pokeapi.client.PokemonClient
import dev.stocky37.pokeapi.jaxrs.api.PokemonService
import dev.stocky37.pokeapi.model.Pokemon
import java.util.stream.Collectors
import javax.ws.rs.NotFoundException

class PokemonJaxrsClient(private val service: PokemonService) : PokemonClient {
	override fun list(limit: Int, offset: Int): List<Pokemon> {
		return service.list(limit, offset).results.parallelStream()
			.map { byName(it.name) }
			.filter { it != null }
			.map { it!! }
			.collect(Collectors.toList())
	}

	override fun byName(name: String): Pokemon? {
		return try {
			service.byName(name)
		} catch(nf: NotFoundException) {
			null
		}
	}

	override fun byId(id: Int): Pokemon? {
		return service.byId(id)
	}

	override fun search(term: String): List<Pokemon> {
		TODO("Not yet implemented")
	}
}