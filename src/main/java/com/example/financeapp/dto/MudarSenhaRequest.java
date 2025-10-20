package com.example.financeapp.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MudarSenhaRequest {

    @NotBlank(message = "A nova senha é obrigatória")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    private String novaSenha;

    @NotBlank(message = "A confirmação da senha é obrigatória")
    private String confirmacaoSenha;

    @AssertTrue(message = "As senhas não conferem")
    private boolean isSenhasConferem() {
        if (novaSenha == null || confirmacaoSenha == null) {
            return false;
        }
        return novaSenha.equals(confirmacaoSenha);
    }
}