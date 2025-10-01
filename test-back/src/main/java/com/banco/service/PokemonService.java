package com.banco.service;

import com.banco.client.PokemonApiClient;
import com.banco.dto.PokemonMoveResponse;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.eventbus.EventBus;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * Service for handling Pokemon API operations
 * Implements fault tolerance strategies: Circuit Breaker and Retry
 */
@ApplicationScoped
@Slf4j
public class PokemonService {

    @Inject
    @RestClient
    PokemonApiClient pokemonApiClient;

    @Inject
    EventBus eventBus;

    /**
     * Retrieves Pokemon moves with fault tolerance strategies
     * @return Uni containing the Pokemon move response
     */
    @Retry(maxRetries = 3, delay = 1000)
    @CircuitBreaker(requestVolumeThreshold = 4, failureRatio = 0.5, delay = 5000)
    @Timeout(value = 30, unit = ChronoUnit.SECONDS)
    public Uni<PokemonMoveResponse> getPokemonMoves() {
        log.info("Fetching Pokemon moves from API");
        
        return pokemonApiClient.getMoves()
                .onItem().invoke(response -> {
                    log.info("Successfully retrieved {} Pokemon moves", response.getCount());
                    // Send non-blocking event
                    eventBus.send("pokemon.moves.fetched", response);
                })
                .onFailure().invoke(failure -> {
                    log.error("Failed to fetch Pokemon moves: {}", failure.getMessage());
                });
    }
}
