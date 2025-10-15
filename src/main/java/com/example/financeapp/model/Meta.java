package com.example.financeapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "metas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Meta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da meta é obrigatório")
    @Column(nullable = false)
    private String nome;

    @NotNull(message = "O valor alvo é obrigatório")
    @Column(name = "valor_alvo", nullable = false)
    private BigDecimal valorAlvo;

    @Column(name = "valor_atual", nullable = false)
    private BigDecimal valorAtual = BigDecimal.ZERO;

    @Column(name = "data_limite")
    private LocalDate dataLimite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
}
