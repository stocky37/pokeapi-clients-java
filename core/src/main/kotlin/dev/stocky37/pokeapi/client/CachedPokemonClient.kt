package dev.stocky37.pokeapi.client

import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.CaffeineSpec
import dev.stocky37.pokeapi.cache.CompositeCacheKey
import dev.stocky37.pokeapi.model.Pokemon
import java.lang.RuntimeException

private const val POKEMON_COUNT_ESTIMATE = 1118

class CachedPokemonClient(
	private val delegate: PokemonClient,
	cacheSpec: CaffeineSpec,
) : PokemonClient {

	private val pokemonCache = Caffeine.from(cacheSpec)
		.initialCapacity(POKEMON_COUNT_ESTIMATE * 2)
		.build { key: Any ->
			when(key) {
				is Int -> delegate.byId(key)
				is String -> delegate.byName(key)
				else -> null
			}
		}

	private val pokemonListCache = Caffeine.from(cacheSpec)
		.initialCapacity(POKEMON_COUNT_ESTIMATE / DEFAULT_BULK_LIMIT)
		.build(fun(key: CompositeCacheKey): List<Pokemon>? {
			return when(key.elements.size) {
				0 -> null
				1 -> delegate.listAll(key.elements[0] as Int)
				else -> delegate.list(key.elements[0] as Int, key.elements[1] as Int)
			}
		})

	override fun listAll(limit: Int): List<Pokemon> {
		return pokemonListCache.get(CompositeCacheKey.from(limit)) ?: emptyList()
	}

	override fun list(limit: Int, offset: Int): List<Pokemon> {
		return pokemonListCache.get(CompositeCacheKey.from(limit, offset)) ?: emptyList()
	}

	override fun byName(name: String): Pokemon? {
		return pokemonCache.get(CompositeCacheKey.from(name))
	}

	override fun byId(id: Int): Pokemon? {
		return pokemonCache.get(CompositeCacheKey.from(id))
	}

	override fun search(term: String): List<Pokemon> {
		TODO("Not yet implemented")
	}
}