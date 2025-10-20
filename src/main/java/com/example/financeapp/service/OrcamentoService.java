package com.example.financeapp.service;

import com.example.financeapp.dto.OrcamentoProgressoDTO;
import com.example.financeapp.dto.OrcamentoRequest;
import com.example.financeapp.dto.OrcamentoResponse;
import com.example.financeapp.model.Categoria;
import com.example.financeapp.model.Orcamento;
import com.example.financeapp.model.Usuario;
import com.example.financeapp.repository.CategoriaRepository;
import com.example.financeapp.repository.OrcamentoRepository;
import com.example.financeapp.repository.TransacaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrcamentoService {

    private final OrcamentoRepository orcamentoRepository;
    private final CategoriaRepository categoriaRepository;
    private final UsuarioLogadoService usuarioLogadoService;
    private final TransacaoRepository transacaoRepository;

    public OrcamentoService(OrcamentoRepository orcamentoRepository, CategoriaRepository categoriaRepository, UsuarioLogadoService usuarioLogadoService, TransacaoRepository transacaoRepository) { // ADICIONAR NO CONSTRUTOR
        this.orcamentoRepository = orcamentoRepository;
        this.categoriaRepository = categoriaRepository;
        this.usuarioLogadoService = usuarioLogadoService;
        this.transacaoRepository = transacaoRepository;
    }

    @Transactional
    public OrcamentoResponse adicionar(OrcamentoRequest request) {
        Usuario usuario = usuarioLogadoService.getUsuarioLogado();
        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        if (!categoria.getUsuario().getId().equals(usuario.getId())) {
            throw new SecurityException("Acesso negado à categoria.");
        }

        orcamentoRepository.findByCategoriaIdAndPeriodoAndUsuarioId(request.getCategoriaId(), request.getPeriodo(), usuario.getId())
                .ifPresent(o -> {
                    throw new IllegalStateException("Já existe um orçamento para esta categoria e período.");
                });

        Orcamento orcamento = Orcamento.builder()
                .valor(request.getValor())
                .periodo(request.getPeriodo())
                .descricao(request.getDescricao())
                .categoria(categoria)
                .usuario(usuario)
                .build();

        Orcamento orcamentoSalvo = orcamentoRepository.save(orcamento);
        return OrcamentoResponse.from(orcamentoSalvo);
    }

    @Transactional(readOnly = true)
    public List<OrcamentoProgressoDTO> listarTodos() {
        Usuario usuario = usuarioLogadoService.getUsuarioLogado();
        List<Orcamento> orcamentos = orcamentoRepository.findAllByUsuarioId(usuario.getId());

        LocalDate hoje = LocalDate.now();

        return orcamentos.stream().map(orcamento -> {
            LocalDate dataInicio;
            LocalDate dataFim;

            if (orcamento.getPeriodo() == Orcamento.PeriodoOrcamento.MENSAL) {
                dataInicio = hoje.withDayOfMonth(1);
                dataFim = hoje.with(YearMonth.from(hoje).atEndOfMonth());
            } else {
                dataInicio = hoje.withDayOfYear(1);
                dataFim = hoje.withDayOfYear(hoje.lengthOfYear());
            }

            BigDecimal gasto = transacaoRepository.sumGastosByCategoriaAndPeriodo(
                    usuario.getId(),
                    orcamento.getCategoria().getId(),
                    dataInicio,
                    dataFim
            );

            BigDecimal valorOrcado = orcamento.getValor();
            BigDecimal restante = valorOrcado.subtract(gasto);


            BigDecimal progresso;
            if (valorOrcado.compareTo(BigDecimal.ZERO) == 0) {
                progresso = BigDecimal.ZERO;
            } else {
                progresso = gasto.multiply(BigDecimal.valueOf(100))
                        .divide(valorOrcado, 2, RoundingMode.HALF_UP);
            }

            return new OrcamentoProgressoDTO(
                    orcamento.getId(),
                    valorOrcado,
                    orcamento.getPeriodo(),
                    orcamento.getCategoria().getNome(),
                    orcamento.getDescricao(),
                    gasto,
                    restante,
                    progresso
            );
        }).collect(Collectors.toList());
    }



    @Transactional
    public OrcamentoResponse atualizar(Long id, OrcamentoRequest request) {
        Usuario usuario = usuarioLogadoService.getUsuarioLogado();
        Orcamento orcamento = orcamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Orçamento não encontrado"));

        if (!orcamento.getUsuario().getId().equals(usuario.getId())) {
            throw new SecurityException("Acesso negado.");
        }

        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        if (!categoria.getUsuario().getId().equals(usuario.getId())) {
            throw new SecurityException("Acesso negado à categoria.");
        }

        Optional<Orcamento> existente = orcamentoRepository.findByCategoriaIdAndPeriodoAndUsuarioId(request.getCategoriaId(), request.getPeriodo(), usuario.getId());
        if (existente.isPresent() && !existente.get().getId().equals(id)) {
            throw new IllegalStateException("Já existe um outro orçamento para esta categoria e período.");
        }

        orcamento.setValor(request.getValor());
        orcamento.setPeriodo(request.getPeriodo());
        orcamento.setDescricao(request.getDescricao());
        orcamento.setCategoria(categoria);

        Orcamento orcamentoAtualizado = orcamentoRepository.save(orcamento);
        return OrcamentoResponse.from(orcamentoAtualizado);
    }

    @Transactional
    public void deletar(Long id) {
        Usuario usuario = usuarioLogadoService.getUsuarioLogado();
        Orcamento orcamento = orcamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Orçamento não encontrado"));

        if (!orcamento.getUsuario().getId().equals(usuario.getId())) {
            throw new SecurityException("Acesso negado.");
        }

        orcamentoRepository.delete(orcamento);
    }
}