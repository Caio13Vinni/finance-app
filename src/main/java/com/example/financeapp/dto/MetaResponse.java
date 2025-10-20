package com.example.financeapp.dto;

import com.example.financeapp.model.Meta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetaResponse {
    private Long id;
    private String nome;
    private BigDecimal valorAlvo;
    private BigDecimal valorAtual;
    private LocalDate dataLimite;
    private String categoriaNome;

    public static MetaResponse from(Meta meta) {
        return new MetaResponse(
                meta.getId(),
                meta.getNome(),
                meta.getValorAlvo(),
                meta.getValorAtual(),
                meta.getDataLimite(),
                meta.getCategoria().getNome()
        );
    }
}