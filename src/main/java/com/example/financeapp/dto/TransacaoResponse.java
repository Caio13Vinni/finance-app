package com.example.financeapp.dto;

import com.example.financeapp.model.Transacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoResponse {
    private Long id;
    private BigDecimal valor;
    private String descricao;
    private LocalDate data;
    private Transacao.TipoTransacao tipo;
    private String categoriaNome;

    public static TransacaoResponse from(Transacao transacao) {
        return new TransacaoResponse(
                transacao.getId(),
                transacao.getValor(),
                transacao.getDescricao(),
                transacao.getData(),
                transacao.getTipo(),
                transacao.getCategoria().getNome()
        );
    }
}