package com.example.financeapp.controller;

import com.example.financeapp.dto.OrcamentoProgressoDTO; // IMPORTAR O NOVO DTO
import com.example.financeapp.dto.OrcamentoRequest;
import com.example.financeapp.dto.OrcamentoResponse;
import com.example.financeapp.service.OrcamentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orcamentos")
public class OrcamentoController {

    private final OrcamentoService orcamentoService;

    public OrcamentoController(OrcamentoService orcamentoService) {
        this.orcamentoService = orcamentoService;
    }

    @PostMapping
    public ResponseEntity<OrcamentoResponse> adicionarOrcamento(@Valid @RequestBody OrcamentoRequest request) {
        OrcamentoResponse response = orcamentoService.adicionar(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrcamentoProgressoDTO>> listarOrcamentos() {
        List<OrcamentoProgressoDTO> orcamentos = orcamentoService.listarTodos();
        return ResponseEntity.ok(orcamentos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrcamentoResponse> atualizarOrcamento(@PathVariable Long id, @Valid @RequestBody OrcamentoRequest request) {
        OrcamentoResponse response = orcamentoService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarOrcamento(@PathVariable Long id) {
        orcamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}