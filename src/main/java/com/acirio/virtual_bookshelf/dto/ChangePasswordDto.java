package com.acirio.virtual_bookshelf.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChangePasswordDto {

    @NotBlank
    @Schema(description = "Senha atual do usuário para validação", example = "SenhaAntiga123!")
    private String currentPassword;

    @NotBlank
    @Schema(description = "Nova senha desejada pelo usuário", example = "SenhaNova456!")
    private String newPassword;

}