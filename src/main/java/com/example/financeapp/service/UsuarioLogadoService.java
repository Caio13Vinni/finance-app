package com.example.financeapp.service;

import com.example.financeapp.model.Usuario;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioLogadoService {

    public Usuario getUsuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || !(authentication.getPrincipal() instanceof Usuario)) {
            throw new IllegalStateException("Nenhum usuário autenticado encontrado.");
        }
        return (Usuario) authentication.getPrincipal();
    }
}