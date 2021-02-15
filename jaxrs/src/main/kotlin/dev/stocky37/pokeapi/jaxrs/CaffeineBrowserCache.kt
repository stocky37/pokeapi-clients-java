package dev.stocky37.pokeapi.jaxrs

import com.github.benmanes.caffeine.cache.Cache
import dev.stocky37.pokeapi.cache.CompositeCacheKey
import org.jboss.resteasy.client.jaxrs.cache.BrowserCache
import org.jboss.resteasy.client.jaxrs.cache.CacheEntry
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.MultivaluedMap

class CaffeineBrowserCache(private val cache: Cache<CompositeCacheKey, BrowserCache.Entry>) :
	BrowserCache {

	override fun getAny(key: String): BrowserCache.Entry? {
		return cache.getIfPresent(CompositeCacheKey.from(key, MediaType.WILDCARD))
	}

	override fun get(key: String, accept: MediaType): BrowserCache.Entry? {
		return cache.getIfPresent(CompositeCacheKey.from(key, accept))
	}

	override fun put(
		key: String,
		mediaType: MediaType,
		headers: MultivaluedMap<String, String>,
		cached: ByteArray,
		expires: Int,
		etag: String,
		lastModified: String?,
	): BrowserCache.Entry {
		return put(CacheEntry(key, headers, cached, expires, etag, lastModified, mediaType))
	}

	fun put(entry: CacheEntry): BrowserCache.Entry {
		cache.asMap().putIfAbsent(CompositeCacheKey.from(entry.key, MediaType.WILDCARD), entry)
		cache.put(CompositeCacheKey.from(entry.key, entry.mediaType.toString()), entry)
		return entry
	}

	override fun remove(key: String, type: MediaType): BrowserCache.Entry? {
		val cacheKey = CompositeCacheKey.from(key, type.toString())
		val entry = cache.getIfPresent(cacheKey) ?: return null
		cache.invalidate(cacheKey)
		return entry
	}

	override fun clear() {
		cache.invalidateAll()
	}
}