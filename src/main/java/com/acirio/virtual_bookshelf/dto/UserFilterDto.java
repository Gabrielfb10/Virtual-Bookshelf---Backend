package com.acirio.virtual_bookshelf.dto;

import com.acirio.virtual_bookshelf.model.enums.UserRoleEnum;
import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class UserFilterDto {
    @Schema(description = "Apelido do usuário para filtro", example = "gabifb")
    private String nickname;
    @Schema(description = "Nome do usuário para filtro", example = "Gabriel Fernandes")
    private String name;
    @Schema(description = "E-mail do usuário para filtro", example = "gabriel@email.com")
    private String email;
    @Schema(description = "Papel do usuário para filtro", example = "ADMIN")
    private UserRoleEnum role;
}
