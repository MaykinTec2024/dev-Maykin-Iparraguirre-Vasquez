package com.banco.event;

import com.banco.dto.PokemonMoveResponse;
import io.quarkus.vertx.ConsumeEvent;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

/**
 * Event consumer for handling event bus messages
 * Processes both blocking and non-blocking events
 */
@ApplicationScoped
@Slf4j
public class EventConsumer {

    /**
     * Handles text concatenation events (blocking)
     * @param concatenatedText the concatenated text result
     * @return acknowledgment message
     */
    @ConsumeEvent("text.concatenated")
    public String handleTextConcatenated(String concatenatedText) {
        log.info("Event received - Text concatenated: {}", concatenatedText);
        // Process the concatenated text if needed
        return "Text concatenation event processed";
    }

    /**
     * Handles Pokemon moves fetched events (non-blocking)
     * @param response the Pokemon move response
     */
    @ConsumeEvent(value = "pokemon.moves.fetched", blocking = false)
    public void handlePokemonMovesFetched(PokemonMoveResponse response) {
        log.info("Event received - Pokemon moves fetched: {} moves available", response.getCount());
        // Process the Pokemon moves data if needed
    }
}
