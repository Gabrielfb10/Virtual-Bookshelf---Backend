package com.acirio.virtual_bookshelf.dto;

import com.acirio.virtual_bookshelf.model.enums.UserRoleEnum;
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
    private String nickname;

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotNull
    private UserRoleEnum role;
}
