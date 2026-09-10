package com.acirio.virtual_bookshelf.dto;

import com.acirio.virtual_bookshelf.model.enums.UserRoleEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserAdminResponseDto {

    @Schema(description = "ID único do usuário", example = "1")
    private Long id;

    @Schema(description = "Apelido do usuário", example = "gabifb")
    private String nickname;

    @Schema(description = "Nome completo do usuário", example = "Gabriel Fernandes")
    private String name;

    @Schema(description = "E-mail do usuário", example = "gabriel@email.com")
    private String email;

    @Schema(description = "Papel do usuário no sistema", example = "ADMIN")
    private UserRoleEnum role;
}