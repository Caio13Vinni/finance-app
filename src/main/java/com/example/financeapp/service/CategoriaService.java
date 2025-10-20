package com.example.financeapp.service;

import com.example.financeapp.dto.CategoriaRequest;
import com.example.financeapp.dto.CategoriaResponse;
import com.example.financeapp.model.Categoria;
import com.example.financeapp.model.Usuario;
import com.example.financeapp.repository.CategoriaRepository;
import com.example.financeapp.repository.TransacaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final TransacaoRepository transacaoRepository;
    private final UsuarioLogadoService usuarioLogadoService;

    public CategoriaService(CategoriaRepository categoriaRepository, TransacaoRepository transacaoRepository, UsuarioLogadoService usuarioLogadoService) {
        this.categoriaRepository = categoriaRepository;
        this.transacaoRepository = transacaoRepository;
        this.usuarioLogadoService = usuarioLogadoService;
    }

    @Transactional
    public CategoriaResponse adicionar(CategoriaRequest request) {
        Usuario usuario = usuarioLogadoService.getUsuarioLogado();

        Categoria categoria = Categoria.builder()
                .nome(request.getNome())
                .usuario(usuario)
                .build();

        Categoria categoriaSalva = categoriaRepository.save(categoria);
        return CategoriaResponse.from(categoriaSalva);
    }

    @Transactional(readOnly = true)
    public List<CategoriaResponse> listarTodas() {
        Usuario usuario = usuarioLogadoService.getUsuarioLogado();
        List<Categoria> categorias = categoriaRepository.findAllByUsuarioId(usuario.getId());
        return categorias.stream()
                .map(CategoriaResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public CategoriaResponse atualizar(Long id, CategoriaRequest request) {
        Usuario usuario = usuarioLogadoService.getUsuarioLogado();
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        if (!categoria.getUsuario().getId().equals(usuario.getId())) {
            throw new SecurityException("Acesso negado.");
        }

        categoria.setNome(request.getNome());
        Categoria categoriaAtualizada = categoriaRepository.save(categoria);
        return CategoriaResponse.from(categoriaAtualizada);
    }

    @Transactional
    public void deletar(Long id) {
        Usuario usuario = usuarioLogadoService.getUsuarioLogado();
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        if (!categoria.getUsuario().getId().equals(usuario.getId())) {
            throw new SecurityException("Acesso negado.");
        }

        // Regra de negócio: Não permitir excluir categorias que estão em uso
        if (transacaoRepository.existsByCategoriaId(id)) {
            throw new IllegalStateException("Não é possível excluir uma categoria que já está sendo utilizada em transações.");
        }

        categoriaRepository.delete(categoria);
    }
}