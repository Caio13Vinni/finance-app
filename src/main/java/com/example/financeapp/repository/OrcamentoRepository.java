package com.example.financeapp.repository;

import com.example.financeapp.model.Orcamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrcamentoRepository extends JpaRepository<Orcamento, Long> {

    boolean existsByCategoriaIdAndPeriodoAndUsuarioId(Long categoriaId, Orcamento.PeriodoOrcamento periodo, Long usuarioId);
}
