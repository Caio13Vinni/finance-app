package com.example.financeapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResumoDTO {

    private BigDecimal totalEntradasMensal;
    private BigDecimal totalSaidasMensal;
    private BigDecimal saldoTotal;
    private List<TransacaoResponse> transacoesRecentes;
}