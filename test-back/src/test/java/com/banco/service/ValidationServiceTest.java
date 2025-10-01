package com.banco.service;

import com.banco.exception.ValidationException;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ValidationService
 */
@QuarkusTest
class ValidationServiceTest {

    @Inject
    ValidationService validationService;

    @Test
    void testValidStringParameter() {
        assertDoesNotThrow(() -> 
            validationService.validateStringParameter("valid string", "test"));
    }

    @Test
    void testNullParameter() {
        assertThrows(ValidationException.class, () -> 
            validationService.validateStringParameter(null, "test"));
    }

    @Test
    void testBlankParameter() {
        assertThrows(ValidationException.class, () -> 
            validationService.validateStringParameter("", "test"));
    }

    @Test
    void testSqlInjectionPrevention() {
        assertThrows(ValidationException.class, () -> 
            validationService.validateStringParameter("SELECT * FROM users", "test"));
    }

    @Test
    void testValidTextConcatenationParams() {
        assertDoesNotThrow(() -> 
            validationService.validateTextConcatenationParams("a", "b", "c", "d", "e"));
    }
}
