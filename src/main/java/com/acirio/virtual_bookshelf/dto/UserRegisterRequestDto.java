package com.acirio.virtual_bookshelf.dto;

import com.acirio.virtual_bookshelf.model.enums.UserRoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRegisterRequestDto {

    @NotBlank()
    private String username;

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    //Valor padrao é definido no mapper
    private UserRoleEnum role;

}
