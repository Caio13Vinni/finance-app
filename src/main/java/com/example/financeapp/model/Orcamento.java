package com.example.financeapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "orcamentos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orcamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O valor do orçamento é obrigatório")
    @Column(nullable = false)
    private BigDecimal valor;

    @NotNull(message = "O período é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PeriodoOrcamento periodo;


    @Column(nullable = true)
    private String descricao;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public enum PeriodoOrcamento {
        MENSAL,
        ANUAL
    }
}