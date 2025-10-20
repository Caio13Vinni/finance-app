package com.example.financeapp.dto;

import com.example.financeapp.model.Orcamento;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrcamentoRequest {

    @NotNull(message = "O valor é obrigatório")
    @Positive(message = "O valor deve ser positivo")
    private BigDecimal valor;

    @NotNull(message = "O período é obrigatório")
    private Orcamento.PeriodoOrcamento periodo;

    @NotNull(message = "O ID da categoria é obrigatório")
    private Long categoriaId;

    private String descricao;
}