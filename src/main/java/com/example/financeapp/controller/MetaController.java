package com.example.financeapp.controller;

import com.example.financeapp.dto.MetaRequest;
import com.example.financeapp.dto.MetaResponse;
import com.example.financeapp.service.MetaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/metas")
public class MetaController {

    private final MetaService metaService;

    public MetaController(MetaService metaService) {
        this.metaService = metaService;
    }

    @PostMapping
    public ResponseEntity<MetaResponse> adicionarMeta(@Valid @RequestBody MetaRequest request) {
        MetaResponse response = metaService.adicionar(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MetaResponse>> listarMetas() {
        List<MetaResponse> metas = metaService.listarTodas();
        return ResponseEntity.ok(metas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetaResponse> atualizarMeta(@PathVariable Long id, @Valid @RequestBody MetaRequest request) {
        MetaResponse response = metaService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMeta(@PathVariable Long id) {
        metaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}