package com.banco.resource;

import com.banco.dto.PokemonMoveResponse;
import com.banco.service.PokemonService;
import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;

/**
 * REST resource for Pokemon API operations
 * Exposes GET endpoint at /api/v2/move with header propagation
 */
@Path("/api/v2/move")
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
public class PokemonResource {

    @Inject
    PokemonService pokemonService;

    /**
     * Retrieves Pokemon moves from external API
     * Supports header propagation for tracing and authentication
     * @param headers HTTP headers from the request
     * @return Uni containing the Pokemon move response
     */
    @GET
    public Uni<PokemonMoveResponse> getPokemonMoves(@Context HttpHeaders headers) {
        log.info("Received request to get Pokemon moves");
        
        // Log propagated headers for debugging
        headers.getRequestHeaders().forEach((key, values) -> {
            if (key.toLowerCase().startsWith("x-") || 
                key.toLowerCase().equals("authorization") ||
                key.toLowerCase().equals("user-agent")) {
                log.debug("Propagating header: {} = {}", key, values);
            }
        });
        
        return pokemonService.getPokemonMoves();
    }
}
