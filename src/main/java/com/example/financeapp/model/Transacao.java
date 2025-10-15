package com.example.financeapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transacoes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O valor é obrigatório")
    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private String descricao;

    @NotNull(message = "A data é obrigatória")
    @Column(nullable = false)
    private LocalDate data;

    @NotNull(message = "O tipo da transação é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTransacao tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public enum TipoTransacao {
        ENTRADA,
        SAIDA
    }
}
