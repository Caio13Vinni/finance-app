package com.example.financeapp.service;

import com.example.financeapp.dto.DashboardResumoDTO;
import com.example.financeapp.dto.TransacaoResponse;
import com.example.financeapp.model.Transacao;
import com.example.financeapp.model.Usuario;
import com.example.financeapp.repository.TransacaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final TransacaoRepository transacaoRepository;
    private final UsuarioLogadoService usuarioLogadoService;

    public DashboardService(TransacaoRepository transacaoRepository, UsuarioLogadoService usuarioLogadoService) {
        this.transacaoRepository = transacaoRepository;
        this.usuarioLogadoService = usuarioLogadoService;
    }

    @Transactional(readOnly = true)
    public DashboardResumoDTO getResumoMensal() {
        Usuario usuario = usuarioLogadoService.getUsuarioLogado();
        Long usuarioId = usuario.getId();

        // Define o período (mês atual)
        LocalDate hoje = LocalDate.now();
        LocalDate dataInicio = hoje.withDayOfMonth(1);
        LocalDate dataFim = hoje.with(YearMonth.from(hoje).atEndOfMonth());
        BigDecimal totalEntradas = transacaoRepository.sumByTipoAndPeriodo(
                usuarioId, Transacao.TipoTransacao.ENTRADA, dataInicio, dataFim
        );


        BigDecimal totalSaidas = transacaoRepository.sumByTipoAndPeriodo(
                usuarioId, Transacao.TipoTransacao.SAIDA, dataInicio, dataFim
        );


        BigDecimal saldoTotal = usuario.getSaldo();

        List<Transacao> transacoes = transacaoRepository.findFirst5ByUsuarioIdOrderByDataDesc(usuarioId);
        List<TransacaoResponse> transacoesRecentes = transacoes.stream()
                .map(TransacaoResponse::from)
                .collect(Collectors.toList());

        return new DashboardResumoDTO(totalEntradas, totalSaidas, saldoTotal, transacoesRecentes);
    }
}