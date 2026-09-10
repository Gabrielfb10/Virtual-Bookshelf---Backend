package com.acirio.virtual_bookshelf.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdminChangePasswordDto {

    @NotBlank
    @Schema(description = "Nova senha a ser definida para o usuário pelo administrador", example = "NovaSenhaAdmin123!")
    private String newPassword;
}