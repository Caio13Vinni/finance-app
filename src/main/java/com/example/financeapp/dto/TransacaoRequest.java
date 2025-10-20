package com.example.financeapp.dto;

import com.example.financeapp.model.Transacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransacaoRequest {

    @NotNull(message = "O valor é obrigatório")
    @Positive(message = "O valor deve ser positivo")
    private BigDecimal valor;

    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    @NotNull(message = "A data é obrigatória")
    private LocalDate data;

    @NotNull(message = "O tipo da transação é obrigatório")
    private Transacao.TipoTransacao tipo;

    @NotNull(message = "O ID da categoria é obrigatório")
    private Long categoriaId;
}