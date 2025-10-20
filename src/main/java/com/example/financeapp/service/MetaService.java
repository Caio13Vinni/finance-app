package com.example.financeapp.service;

import com.example.financeapp.dto.MetaRequest;
import com.example.financeapp.dto.MetaResponse;
import com.example.financeapp.model.Categoria;
import com.example.financeapp.model.Meta;
import com.example.financeapp.model.Usuario;
import com.example.financeapp.repository.CategoriaRepository;
import com.example.financeapp.repository.MetaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MetaService {

    private final MetaRepository metaRepository;
    private final CategoriaRepository categoriaRepository;
    private final UsuarioLogadoService usuarioLogadoService;

    public MetaService(MetaRepository metaRepository, CategoriaRepository categoriaRepository, UsuarioLogadoService usuarioLogadoService) {
        this.metaRepository = metaRepository;
        this.categoriaRepository = categoriaRepository;
        this.usuarioLogadoService = usuarioLogadoService;
    }

    @Transactional
    public MetaResponse adicionar(MetaRequest request) {
        Usuario usuario = usuarioLogadoService.getUsuarioLogado();
        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        if (!categoria.getUsuario().getId().equals(usuario.getId())) {
            throw new SecurityException("Acesso negado à categoria.");
        }

        Meta meta = Meta.builder()
                .nome(request.getNome())
                .valorAlvo(request.getValorAlvo())
                .valorAtual(request.getValorAtual())
                .dataLimite(request.getDataLimite())
                .categoria(categoria)
                .usuario(usuario)
                .build();

        Meta metaSalva = metaRepository.save(meta);
        return MetaResponse.from(metaSalva);
    }

    @Transactional(readOnly = true)
    public List<MetaResponse> listarTodas() {
        Usuario usuario = usuarioLogadoService.getUsuarioLogado();
        List<Meta> metas = metaRepository.findAllByUsuarioId(usuario.getId());
        return metas.stream()
                .map(MetaResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public MetaResponse atualizar(Long id, MetaRequest request) {
        Usuario usuario = usuarioLogadoService.getUsuarioLogado();
        Meta meta = metaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Meta não encontrada"));

        if (!meta.getUsuario().getId().equals(usuario.getId())) {
            throw new SecurityException("Acesso negado.");
        }

        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        if (!categoria.getUsuario().getId().equals(usuario.getId())) {
            throw new SecurityException("Acesso negado à categoria.");
        }

        meta.setNome(request.getNome());
        meta.setValorAlvo(request.getValorAlvo());
        meta.setValorAtual(request.getValorAtual());
        meta.setDataLimite(request.getDataLimite());
        meta.setCategoria(categoria);

        Meta metaAtualizada = metaRepository.save(meta);
        return MetaResponse.from(metaAtualizada);
    }

    @Transactional
    public void deletar(Long id) {
        Usuario usuario = usuarioLogadoService.getUsuarioLogado();
        Meta meta = metaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Meta não encontrada"));

        if (!meta.getUsuario().getId().equals(usuario.getId())) {
            throw new SecurityException("Acesso negado.");
        }

        metaRepository.delete(meta);
    }
}