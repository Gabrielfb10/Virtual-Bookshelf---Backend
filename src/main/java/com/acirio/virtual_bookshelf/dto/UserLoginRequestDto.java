package com.acirio.virtual_bookshelf.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserLoginRequestDto {

    @NotBlank
    @Email
    @Schema(description = "E-mail cadastrado do usuário", example = "usuario@email.com")
    private String email;

    @NotBlank
    @Schema(description = "Senha do usuário", example = "Senha123!")
    private String password;
}
