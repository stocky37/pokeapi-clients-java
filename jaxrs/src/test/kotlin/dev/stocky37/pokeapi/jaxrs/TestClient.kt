package dev.stocky37.pokeapi.jaxrs;

import com.github.benmanes.caffeine.cache.Caffeine
import dev.stocky37.pokeapi.client.CachedPokeApiClient
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.time.Duration
import kotlin.system.measureTimeMillis


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestClient {
	@Test
	fun works() {
		Caffeine.newBuilder()
		val client_ = PokeApiJaxrsClientBuilder(
			cache = CaffeineBrowserCache(Caffeine.newBuilder().build())
		).build()
		val client = CachedPokeApiClient(client_)

		//~1.043s
		println(Duration.ofMillis(measureTimeMillis { println(client.pokemon.listAll().size) }))
		// ~0.237, 0.264, 0.178, 0.186
		println(Duration.ofMillis(measureTimeMillis { println(client.pokemon.listAll()) }))
		println(Duration.ofMillis(measureTimeMillis { println(client.pokemon.listAll()) }))
	}
}
