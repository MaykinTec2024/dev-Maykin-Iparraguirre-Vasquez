package com.banco.resource;

import com.banco.dto.TextConcatenationRequest;
import com.banco.dto.TextConcatenationResponse;
import com.banco.service.TextConcatenationService;
import lombok.extern.slf4j.Slf4j;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * REST resource for text concatenation operations
 * Exposes POST endpoint at /api/v1/test
 */
@Path("/api/v1/test")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
public class TextConcatenationResource {

    @Inject
    TextConcatenationService textConcatenationService;

    /**
     * Concatenates 5 string parameters and returns the result
     * @param request the concatenation request containing 5 parameters
     * @return the concatenated text result
     */
    @POST
    public Response concatenateText(TextConcatenationRequest request) {
        log.info("Received text concatenation request");
        
        try {
            TextConcatenationResponse response = textConcatenationService.concatenateText(request);
            return Response.ok(response.getResult()).build();
        } catch (Exception e) {
            log.error("Error processing text concatenation: {}", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error: " + e.getMessage())
                    .build();
        }
    }
}
