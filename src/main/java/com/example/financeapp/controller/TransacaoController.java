package com.example.financeapp.controller;

import com.example.financeapp.dto.TransacaoRequest;
import com.example.financeapp.dto.TransacaoResponse;
import com.example.financeapp.service.TransacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transacoes")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping
    public ResponseEntity<TransacaoResponse> adicionarTransacao(@Valid @RequestBody TransacaoRequest request) {
        TransacaoResponse response = transacaoService.adicionar(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TransacaoResponse>> listarTransacoes() {
        List<TransacaoResponse> transacoes = transacaoService.listarTodas();
        return ResponseEntity.ok(transacoes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTransacao(@PathVariable Long id) {
        transacaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}