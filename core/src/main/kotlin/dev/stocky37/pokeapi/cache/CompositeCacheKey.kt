package dev.stocky37.pokeapi.cache

data class CompositeCacheKey constructor(val elements: Array<out Any>) {
	init {
		require(elements.isNotEmpty()) { "At least one key element is required to create a composite cache key instance" }
	}

	override fun equals(other: Any?): Boolean {
		if(this === other) return true
		if(javaClass != other?.javaClass) return false
		other as CompositeCacheKey
		return elements.contentDeepEquals(other.elements)
	}

	override fun hashCode(): Int {
		return elements.contentDeepHashCode()
	}

	companion object {
		fun from(vararg elements: Any): CompositeCacheKey {
			return CompositeCacheKey(elements)
		}
	}
}