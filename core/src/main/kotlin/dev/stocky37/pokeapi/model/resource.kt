package dev.stocky37.pokeapi.model

data class NamedApiResource(val name: String, val url: String)

data class NamedApiResourceList(
	val count: Int,
	val next: String?,
	val previous: String?,
	val results: List<NamedApiResource>
)
