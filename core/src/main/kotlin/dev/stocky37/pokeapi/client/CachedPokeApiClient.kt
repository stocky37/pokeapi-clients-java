package dev.stocky37.pokeapi.client

import com.github.benmanes.caffeine.cache.CaffeineSpec

class CachedPokeApiClient(
	delegate: PokeApiClient,
	cacheSpec: CaffeineSpec = CaffeineSpec.parse("refreshAfterWrite=1d"),
) : PokeApiClient {
	override val pokemon by lazy { CachedPokemonClient(delegate.pokemon, cacheSpec) }
}