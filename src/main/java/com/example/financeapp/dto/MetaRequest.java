package com.example.financeapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class MetaRequest {

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotNull(message = "O valor alvo é obrigatório")
    @Positive(message = "O valor alvo deve ser positivo")
    private BigDecimal valorAlvo;

    @NotNull(message = "O valor atual é obrigatório")
    @PositiveOrZero(message = "O valor atual deve ser zero ou positivo")
    private BigDecimal valorAtual;

    private LocalDate dataLimite;

    @NotNull(message = "O ID da categoria é obrigatório")
    private Long categoriaId;
}