package com.banco.service;

import com.banco.exception.ValidationException;
import org.apache.commons.lang3.StringUtils;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.regex.Pattern;

/**
 * Service for input validation and security checks
 * Prevents SQL injection and validates input parameters
 */
@ApplicationScoped
public class ValidationService {

    private static final Pattern SQL_INJECTION_PATTERN = Pattern.compile(
            "(?i).*(union|select|insert|update|delete|drop|create|alter|exec|execute|script|javascript|vbscript|onload|onerror).*"
    );

    /**
     * Validates a string parameter for null, blank, and SQL injection
     * @param value the string to validate
     * @param paramName the parameter name for error messages
     * @throws ValidationException if validation fails
     */
    public void validateStringParameter(String value, String paramName) {
        if (StringUtils.isBlank(value)) {
            throw new ValidationException("Parameter " + paramName + " cannot be null or blank");
        }
        
        if (SQL_INJECTION_PATTERN.matcher(value).matches()) {
            throw new ValidationException("Parameter " + paramName + " contains potentially dangerous content");
        }
    }

    /**
     * Validates all parameters for a text concatenation request
     * @param param1 first parameter
     * @param param2 second parameter
     * @param param3 third parameter
     * @param param4 fourth parameter
     * @param param5 fifth parameter
     */
    public void validateTextConcatenationParams(String param1, String param2, String param3, String param4, String param5) {
        validateStringParameter(param1, "param1");
        validateStringParameter(param2, "param2");
        validateStringParameter(param3, "param3");
        validateStringParameter(param4, "param4");
        validateStringParameter(param5, "param5");
    }
}
