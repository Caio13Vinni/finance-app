package com.example.financeapp.controller;

import com.example.financeapp.dto.MudarSenhaRequest;
import com.example.financeapp.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/mudar-senha")
    public ResponseEntity<String> mudarSenha(@Valid @RequestBody MudarSenhaRequest request) {
        usuarioService.mudarSenha(request);
        return ResponseEntity.ok("Senha atualizada com sucesso!");
    }
}