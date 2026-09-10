package com.acirio.virtual_bookshelf.dto;

import com.acirio.virtual_bookshelf.model.enums.UserRoleEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserAdminRequestDto {

    @NotBlank
    @Schema(description = "Apelido do usuário para exibição", example = "gabifb")
    private String nickname;

    @NotBlank
    @Schema(description = "Nome completo do usuário", example = "Gabriel Fernandes")
    private String name;

    @NotBlank
    @Email
    @Schema(description = "E-mail do usuário", example = "gabriel@email.com")
    private String email;

    @NotNull
    @Schema(description = "Papel do usuário no sistema", example = "ADMIN")
    private UserRoleEnum role;
}
