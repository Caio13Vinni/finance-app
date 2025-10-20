package com.example.financeapp.service;

import com.example.financeapp.dto.TransacaoRequest;
import com.example.financeapp.dto.TransacaoResponse;
import com.example.financeapp.model.Categoria;
import com.example.financeapp.model.Transacao;
import com.example.financeapp.model.Usuario;
import com.example.financeapp.repository.CategoriaRepository;
import com.example.financeapp.repository.TransacaoRepository;
import com.example.financeapp.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final UsuarioLogadoService usuarioLogadoService;
    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;

    public TransacaoService(TransacaoRepository transacaoRepository, UsuarioLogadoService usuarioLogadoService, CategoriaRepository categoriaRepository, UsuarioRepository usuarioRepository) {
        this.transacaoRepository = transacaoRepository;
        this.usuarioLogadoService = usuarioLogadoService;
        this.categoriaRepository = categoriaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public TransacaoResponse adicionar(TransacaoRequest request) {
        Usuario usuario = usuarioLogadoService.getUsuarioLogado();

        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        // Garante que a categoria pertence ao usuário (importante para segurança)
        if (!categoria.getUsuario().getId().equals(usuario.getId())) {
            throw new SecurityException("Acesso negado à categoria.");
        }

        Transacao transacao = Transacao.builder()
                .valor(request.getValor())
                .descricao(request.getDescricao())
                .data(request.getData())
                .tipo(request.getTipo())
                .categoria(categoria)
                .usuario(usuario)
                .build();

        atualizarSaldo(usuario, transacao.getValor(), transacao.getTipo());

        Transacao transacaoSalva = transacaoRepository.save(transacao);
        usuarioRepository.save(usuario);

        return TransacaoResponse.from(transacaoSalva);
    }

    @Transactional(readOnly = true)
    public List<TransacaoResponse> listarTodas() {
        Usuario usuario = usuarioLogadoService.getUsuarioLogado();
        List<Transacao> transacoes = transacaoRepository.findAllByUsuarioId(usuario.getId());
        return transacoes.stream()
                .map(TransacaoResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deletar(Long id) {
        Usuario usuario = usuarioLogadoService.getUsuarioLogado();
        Transacao transacao = transacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transação não encontrada"));

        if (!transacao.getUsuario().getId().equals(usuario.getId())) {
            throw new SecurityException("Não é permitido excluir transações de outro usuário.");
        }

        // Reverte a operação no saldo
        atualizarSaldo(usuario, transacao.getValor(), transacao.getTipo() == Transacao.TipoTransacao.ENTRADA ? Transacao.TipoTransacao.SAIDA : Transacao.TipoTransacao.ENTRADA);

        transacaoRepository.delete(transacao);
        usuarioRepository.save(usuario);
    }

    private void atualizarSaldo(Usuario usuario, BigDecimal valor, Transacao.TipoTransacao tipo) {
        if (tipo == Transacao.TipoTransacao.ENTRADA) {
            usuario.setSaldo(usuario.getSaldo().add(valor));
        } else {
            usuario.setSaldo(usuario.getSaldo().subtract(valor));
        }
    }
}