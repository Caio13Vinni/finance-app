package com.example.financeapp.repository;

import com.example.financeapp.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    List<Transacao> findAllByUsuarioId(Long usuarioId);

    boolean existsByCategoriaId(Long categoriaId);

    @Query("SELECT COALESCE(SUM(t.valor), 0) FROM Transacao t " +
            "WHERE t.usuario.id = :usuarioId " +
            "AND t.tipo = :tipo " +
            "AND t.data BETWEEN :dataInicio AND :dataFim")
    BigDecimal sumByTipoAndPeriodo(
            @Param("usuarioId") Long usuarioId,
            @Param("tipo") Transacao.TipoTransacao tipo,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim
    );

    List<Transacao> findFirst5ByUsuarioIdOrderByDataDesc(Long usuarioId);


    @Query("SELECT COALESCE(SUM(t.valor), 0) FROM Transacao t " +
            "WHERE t.usuario.id = :usuarioId " +
            "AND t.categoria.id = :categoriaId " +
            "AND t.tipo = com.example.financeapp.model.Transacao.TipoTransacao.SAIDA " +
            "AND t.data BETWEEN :dataInicio AND :dataFim")
    BigDecimal sumGastosByCategoriaAndPeriodo(
            @Param("usuarioId") Long usuarioId,
            @Param("categoriaId") Long categoriaId,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim
    );
}