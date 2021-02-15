package dev.stocky37.pokeapi.client

import dev.stocky37.pokeapi.model.Pokemon
import java.util.concurrent.atomic.AtomicInteger

internal const val DEFAULT_LIMIT = 20
internal const val DEFAULT_OFFSET = 20
internal const val DEFAULT_BULK_LIMIT = 50

interface PokemonClient {
	fun list(limit: Int = DEFAULT_LIMIT, offset: Int = DEFAULT_OFFSET): List<Pokemon>
	fun search(term: String): List<Pokemon>
	fun byName(name: String): Pokemon?
	fun byId(id: Int): Pokemon?

	fun listAll(limit: Int = DEFAULT_BULK_LIMIT): List<Pokemon> {
		val offset = AtomicInteger();
		val pokemon = mutableListOf<Pokemon>()

		do {
			val page = list(limit, offset.getAndAdd(limit))
			pokemon.addAll(page)
		} while(page.isNotEmpty())

		return pokemon
	}
}