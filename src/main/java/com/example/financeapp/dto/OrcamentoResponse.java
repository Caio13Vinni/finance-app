package com.example.financeapp.dto;

import com.example.financeapp.model.Orcamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrcamentoResponse {
    private Long id;
    private BigDecimal valor;
    private Orcamento.PeriodoOrcamento periodo;
    private String categoriaNome;
    private String descricao;

    public static OrcamentoResponse from(Orcamento orcamento) {
        return new OrcamentoResponse(
                orcamento.getId(),
                orcamento.getValor(),
                orcamento.getPeriodo(),
                orcamento.getCategoria().getNome(),
                orcamento.getDescricao()
        );
    }
}