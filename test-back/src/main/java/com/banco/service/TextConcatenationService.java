package com.banco.service;

import com.banco.dto.TextConcatenationRequest;
import com.banco.dto.TextConcatenationResponse;
import io.vertx.mutiny.core.eventbus.EventBus;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * Service for handling text concatenation operations
 * Uses blocking event bus for synchronous processing
 */
@ApplicationScoped
@Slf4j
public class TextConcatenationService {

    @Inject
    ValidationService validationService;

    @Inject
    EventBus eventBus;

    /**
     * Concatenates five string parameters after validation
     * @param request the concatenation request
     * @return the concatenation response
     */
    public TextConcatenationResponse concatenateText(TextConcatenationRequest request) {
        log.info("Processing text concatenation request");
        
        // Validate all parameters
        validationService.validateTextConcatenationParams(
                request.getParam1(),
                request.getParam2(),
                request.getParam3(),
                request.getParam4(),
                request.getParam5()
        );

        // Concatenate parameters
        String result = String.join(" ", 
                request.getParam1(),
                request.getParam2(),
                request.getParam3(),
                request.getParam4(),
                request.getParam5()
        );

        // Send blocking event
        eventBus.requestAndAwait("text.concatenated", result);

        log.info("Text concatenation completed successfully");
        return new TextConcatenationResponse(result, "Text concatenated successfully");
    }
}
