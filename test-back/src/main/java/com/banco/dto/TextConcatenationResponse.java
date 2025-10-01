package com.banco.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * DTO for text concatenation response
 * Contains the concatenated result
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TextConcatenationResponse {
    private String result;
    private String message;
}
