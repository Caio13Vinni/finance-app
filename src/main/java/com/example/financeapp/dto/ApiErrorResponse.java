package com.example.financeapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorResponse {

    private int statusCode;
    private String error;
    private String message;
    private String path;
    private Instant timestamp;
}