package com.example.financeapp.controller;

import com.example.financeapp.dto.DashboardResumoDTO;
import com.example.financeapp.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/resumo-mensal")
    public ResponseEntity<DashboardResumoDTO> getResumoMensal() {
        DashboardResumoDTO resumo = dashboardService.getResumoMensal();
        return ResponseEntity.ok(resumo);
    }
}