package com.banco.interceptor;

import lombok.extern.slf4j.Slf4j;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Interceptor for logging HTTP requests and responses
 */
@Provider
@Slf4j
public class LoggingInterceptor implements ContainerRequestFilter, ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        log.info("Incoming request: {} {} from {}",
                requestContext.getMethod(),
                requestContext.getUriInfo().getPath(),
                requestContext.getHeaderString("User-Agent"));
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        log.info("Outgoing response: {} {} -> Status: {}",
                requestContext.getMethod(),
                requestContext.getUriInfo().getPath(),
                responseContext.getStatus());
    }
}
