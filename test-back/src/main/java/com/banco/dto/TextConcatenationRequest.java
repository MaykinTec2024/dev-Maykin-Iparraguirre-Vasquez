package com.banco.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * DTO for text concatenation request
 * Contains 5 string parameters to be concatenated
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TextConcatenationRequest {
    private String param1;
    private String param2;
    private String param3;
    private String param4;
    private String param5;
}
