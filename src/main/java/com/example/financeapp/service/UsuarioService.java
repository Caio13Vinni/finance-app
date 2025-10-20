package com.example.financeapp.service;

import com.example.financeapp.dto.MudarSenhaRequest;
import com.example.financeapp.model.Usuario;
import com.example.financeapp.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final UsuarioLogadoService usuarioLogadoService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioLogadoService usuarioLogadoService, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioLogadoService = usuarioLogadoService;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void mudarSenha(MudarSenhaRequest request) {

        Usuario usuario = usuarioLogadoService.getUsuarioLogado();

        String senhaCodificada = passwordEncoder.encode(request.getNovaSenha());

        usuario.setSenha(senhaCodificada);

        usuarioRepository.save(usuario);
    }
}