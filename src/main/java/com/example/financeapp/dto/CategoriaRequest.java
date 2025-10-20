package com.example.financeapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoriaRequest {

    @NotBlank(message = "O nome da categoria é obrigatório")
    private String nome;
}