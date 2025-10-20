package com.example.financeapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiValidationErrorResponse {

    private int statusCode;
    private String error;
    private String message;
    private String path;
    private Instant timestamp;
    private Map<String, String> fieldErrors;
}