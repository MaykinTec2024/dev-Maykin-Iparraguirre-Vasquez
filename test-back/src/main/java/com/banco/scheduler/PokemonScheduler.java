package com.banco.scheduler;

import com.banco.service.PokemonService;
import io.quarkus.scheduler.Scheduled;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * Scheduled task for periodic Pokemon API calls
 * Configurable cron expression for flexibility
 */
@ApplicationScoped
@Slf4j
public class PokemonScheduler {

    @Inject
    PokemonService pokemonService;

    @ConfigProperty(name = "scheduler.pokemon.cron", defaultValue = "0 */5 * * * ?")
    String cronExpression;

    /**
     * Scheduled method that calls Pokemon API every 5 minutes
     * Cron expression is configurable via application properties
     */
    @Scheduled(cron = "{scheduler.pokemon.cron}")
    public void fetchPokemonMoves() {
        log.info("Scheduled Pokemon API call started");
        
        pokemonService.getPokemonMoves()
                .subscribe()
                .with(
                        response -> log.info("Scheduled Pokemon API call completed successfully"),
                        failure -> log.error("Scheduled Pokemon API call failed: {}", failure.getMessage())
                );
    }
}
