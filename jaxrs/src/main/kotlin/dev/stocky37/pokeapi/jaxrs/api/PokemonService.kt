package dev.stocky37.pokeapi.jaxrs.api

import dev.stocky37.pokeapi.model.NamedApiResourceList
import dev.stocky37.pokeapi.model.Pokemon
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.MediaType

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
interface PokemonService {
	@GET
	fun list(
		@QueryParam("limit") limit: Int = 20,
		@QueryParam("offset") offset: Int = 0
	): NamedApiResourceList

	@GET
	@Path("{id}")
	fun byId(@PathParam("id") id: Int): Pokemon?

	@GET
	@Path("{name}")
	fun byName(@PathParam("name") name: String): Pokemon?
}