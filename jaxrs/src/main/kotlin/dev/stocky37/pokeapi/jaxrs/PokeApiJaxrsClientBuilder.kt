package dev.stocky37.pokeapi.jaxrs

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import dev.stocky37.pokeapi.jaxrs.api.PokeApiService
import org.jboss.resteasy.client.jaxrs.cache.BrowserCache
import org.jboss.resteasy.client.jaxrs.cache.BrowserCacheFeature
import org.jboss.resteasy.client.jaxrs.cache.LightweightBrowserCache
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl
import javax.ws.rs.core.UriBuilder


class PokeApiJaxrsClientBuilder(
	url: String = "https://pokeapi.co/api",
	cache: BrowserCache = LightweightBrowserCache(),
) {

	fun build(): PokeApiJaxrsClient {
		return PokeApiJaxrsClient(proxyClient)
	}

	private val proxyClient by lazy {
		val cacheFeature = BrowserCacheFeature()
		cacheFeature.cache = cache;
		ResteasyClientBuilderImpl()
			.register(provider, 100)
			.register(cacheFeature)
			.build()
			.target(UriBuilder.fromPath(url))
			.proxyBuilder(PokeApiService::class.java).build()
	}

	private val mapper by lazy {
		ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
			.registerKotlinModule()
	}

	private val provider by lazy {
		val provider = CustomJacksonProvider()
		provider.setMapper(mapper)
		provider
	}
}