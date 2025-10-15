package com.example.financeapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Column(nullable = false)
    private String nome;

    @Email(message = "Email deve ser válido")
    @NotBlank(message = "O email é obrigatório")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private BigDecimal saldo = BigDecimal.ZERO;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transacao> transacoes;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Categoria> categorias;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Meta> metas;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Orcamento> orcamentos;
}
