package com.example.financeapp.repository;

import com.example.financeapp.model.Orcamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;     // Adicione esta importação
import java.util.Optional; // Adicione esta importação

@Repository
public interface OrcamentoRepository extends JpaRepository<Orcamento, Long> {

    Optional<Orcamento> findByCategoriaIdAndPeriodoAndUsuarioId(Long categoriaId, Orcamento.PeriodoOrcamento periodo, Long usuarioId);

    List<Orcamento> findAllByUsuarioId(Long usuarioId);
}