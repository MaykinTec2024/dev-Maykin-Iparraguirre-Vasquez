package com.banco.client;

import com.banco.dto.PokemonMoveResponse;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import io.smallrye.mutiny.Uni;

/**
 * REST client for consuming Pokemon API
 * Configured with fault tolerance strategies
 */
@RegisterRestClient(configKey = "pokemon-api")
@Path("/api/v2")
public interface PokemonApiClient {

    /**
     * Retrieves Pokemon moves from the API
     * @return Uni containing the Pokemon move response
     */
    @GET
    @Path("/move")
    @Produces(MediaType.APPLICATION_JSON)
    Uni<PokemonMoveResponse> getMoves();
}
