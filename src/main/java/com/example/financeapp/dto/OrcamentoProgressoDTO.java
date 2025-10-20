package com.example.financeapp.dto;

import com.example.financeapp.model.Orcamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrcamentoProgressoDTO {


    private Long id;
    private BigDecimal valorOrcado;
    private Orcamento.PeriodoOrcamento periodo;
    private String categoriaNome;
    private String descricao;

    private BigDecimal gasto;
    private BigDecimal restante;
    private BigDecimal progresso;
}